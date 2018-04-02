package com.example.ksachdev.myringtonemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.AudioManager;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by ksachdev on 2018-03-31.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final int DATABASE_VERSION = 6;
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

    private static DatabaseHelper dbInstance;



    @Override
    public void onCreate(SQLiteDatabase db) {
        String createEventsTable = "CREATE TABLE " + TABLE_NAME +
                "(" +
                    KEY_ID + "INTEGER PRIMARY KEY, " +
                    KEY_TITLE + " TEXT, " +
                    KEY_DESC + " TEXT, " +
                    KEY_STARTDATE + " TEXT, " +
                    KEY_START_TIME + " TEXT, " +
                    KEY_END_DATE + " TEXT, " +
                    KEY_ENDTIME + " TEXT, " +
                    KEY_STARTID + " TEXT, " +
                    KEY_ENDID + " TEXT" +
                ")";

        db.execSQL(createEventsTable);
        Log.i(TAG,"created db table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
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
    }

    //database operations

    public void addEvents(Event event){
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.insert(TABLE_NAME,null,getValuesToInsert(event));
            Log.i(TAG,"successfully inserted a record");
        }catch(Error e){
            Log.i(TAG,"Failed to insert record message: " +e.getMessage());
        }

    }

    public void deleteEvent(int id){
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.delete(TABLE_NAME,KEY_ID + "= ?", new String[] {String.valueOf(id)});
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
                        cursor.getString(4),cursor.getString(5),cursor.getString(6));
                String[] alarmId = {cursor.getString(7),cursor.getString(8)};
                event.setAlarmID(alarmId);
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


        values.put(KEY_STARTID,getCount() + ".0");
        values.put(KEY_ENDID,getCount() + ".1");

        return values;

    }

    public int getCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor.getCount();
    }

}
