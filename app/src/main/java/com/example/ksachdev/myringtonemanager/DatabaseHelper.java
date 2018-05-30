package com.example.ksachdev.myringtonemanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by ksachdev on 2018-03-31.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final int DATABASE_VERSION = 20;
    private static final String DATABASE_NAME = "EventsDB";
    private static final String TABLE_NAME = "MyEvents";

    private static final String KEY_ID = "id";
    private static  final String KEY_TITLE = "title";
    private static final String KEY_DESC = "description";
    private static  final String KEY_STARTDATE = "startdate";
    private static final String KEY_START_TIME = "startTime";
    private static final String KEY_END_DATE = "endDate";
    private static final String KEY_ENDTIME = "endtime";
    private static final String KEY_STARTID = "startID";
    private static final String KEY_ENDID = "endID";
    private static final String KEY_IMGRESOURCE = "imgResource";


    private static DatabaseHelper dbInstance;

    Context mContext;




    @Override
    public void onCreate(SQLiteDatabase db) {
        String createEventsTable = "CREATE TABLE " + TABLE_NAME +
                "(" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_TITLE + " TEXT, " +
                    KEY_DESC + " TEXT, " +
                    KEY_STARTDATE + " TEXT, " +
                    KEY_START_TIME + " TEXT, " +
                    KEY_END_DATE + " TEXT, " +
                    KEY_ENDTIME + " TEXT, " +
                    KEY_STARTID + " INTEGER, " +
                    KEY_ENDID + " INTEGER, " +
                    KEY_IMGRESOURCE + " INTEGER" +

                ")";

        db.execSQL(createEventsTable);
        Log.i(TAG,"created db table");
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        Log.i(TAG,c.getColumnNames()[9]+"");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
            Log.i(TAG,"db upgraded");
        }
    }

    public static synchronized DatabaseHelper getInstance(Context context){
        if(dbInstance == null){
            Log.i(TAG,"got db instance");
            dbInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return dbInstance;
    }

    private DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.mContext = context;
    }

    //database operations

    public void addEvents(Event event,String[] mode){
        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues values = getValuesToInsert(event);
            db.insert(TABLE_NAME,null,values);
            Log.i(TAG,"successfully inserted a record");

            //schedule alarm for silent event
            AlarmManager am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

            //set alarm to begin silent event
            Intent mIntent = new Intent(mContext,AlarmReciever.class);
            mIntent.putExtra("mode","start");
            Log.i(TAG,mode[1] + "");
            mIntent.putExtra("action",mode[0]);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext,values.getAsInteger(KEY_STARTID),mIntent,PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            Tools tools = new Tools();

            calendar.setTime(tools.getDate(values.getAsString(KEY_STARTDATE),values.getAsString(KEY_START_TIME)));

            am.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

            //set alarm to end silent event
            Intent mIntent2 = new Intent(mContext,AlarmReciever.class);
            mIntent2.putExtra("mode","end");
            mIntent2.putExtra("key",event.getEndTime());
            mIntent.putExtra("action",mode[1]);
            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(mContext,values.getAsInteger(KEY_ENDID),mIntent2,PendingIntent.FLAG_UPDATE_CURRENT);

            calendar.setTime(tools.getDate(values.getAsString(KEY_END_DATE),values.getAsString(KEY_ENDTIME)));
            am.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent2);

            Toast.makeText(mContext,"Scheduled Silent Event",Toast.LENGTH_LONG).show();


        }catch(Error e){
            Log.i(TAG,"Failed to insert record message: " +e.getMessage());
        }

    }

    public void deleteEvent(int id){
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.delete(TABLE_NAME, KEY_ID + "=?", new String[] {String.valueOf(id)});
            Log.i(TAG,"successfully deleted record id = " + id);
        }catch(Error e){
            Log.i(TAG,"Failed to Delete a record message: " + e.getMessage());
        }
    }

    public ArrayList<Event> getAllEvents(){

        SQLiteDatabase db = getReadableDatabase();


        ArrayList<Event> events = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        if(cursor.moveToFirst()){
            do{
                Event event = new Event(cursor.getString(1),cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getInt(9));
                int[] alarmId = {cursor.getInt(7),cursor.getInt(8)};
                event.setAlarmID(alarmId);
                event.setKey(cursor.getInt(0));
                Log.i(TAG,event.getImgResource()+"");
                events.add(event);
            }while(cursor.moveToNext());

        }
        Log.i(TAG,"successfully executed get all events");
        return events;
    }

    private ContentValues getValuesToInsert(Event event){
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE,event.getTitle());
        values.put(KEY_DESC,event.getDesc());
        values.put(KEY_STARTDATE,event.getStartDate());
        values.put(KEY_START_TIME,event.getStartTime());
        values.put(KEY_END_DATE,event.getEndDate());
        values.put(KEY_ENDTIME,event.getEndTime());

        values.put(KEY_IMGRESOURCE,event.getImgResource());
        values.put(KEY_STARTID,getCount());
        values.put(KEY_ENDID,0 - getCount());

        return values;

    }
    //search for event on the database using endTime since it is unique
    public void updateEvent(String endTime){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IMGRESOURCE,1);

        db.update(TABLE_NAME,values,KEY_ENDTIME + "=?",new String[]{endTime});

    }

    public int getCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor.getCount();
    }

}
