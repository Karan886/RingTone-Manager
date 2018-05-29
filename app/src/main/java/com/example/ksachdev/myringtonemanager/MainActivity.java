package com.example.ksachdev.myringtonemanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity{

    DatabaseHelper db;
    EventsListAdapter adapter;
    ListView listView;
    private String[] collisonSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showScheduleEventsActivity();
            }
        });

        renderList();
        ListView lview = (ListView) findViewById(R.id.events_listview);
        registerForContextMenu(lview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.row_options, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        View view = info.targetView;
        switch (item.getItemId()) {
            case R.id.cacel_row_event:
                Event event = adapter.getItem(position);
                AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);

                Intent mIntent = new Intent(getApplicationContext(), AlarmReciever.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),event.getAlarmID()[0],mIntent,0);
                am.cancel(pendingIntent);

                Intent mIntent2 = new Intent(getApplicationContext(),AlarmReciever.class);
                PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext(),event.getAlarmID()[1],mIntent2,0);
                am.cancel(pendingIntent2);

                db.deleteEvent(event.getKey());
                adapter.remove(event);
                break;
            default:
                return super.onContextItemSelected(item);

        }
        return super.onContextItemSelected(item);
    }

    public void showScheduleEventsActivity(){

        Intent intent = new Intent(this,ScheduleEventsActivity.class);
        intent.putExtra("set",collisonSet);
        startActivity(intent);
    }

    public void renderList(){
        TextView emptyList_txt = (TextView) findViewById(R.id.temp_txt);
        db = DatabaseHelper.getInstance(this);
        ArrayList<Event> events = db.getAllEvents();
        collisonSet = new String[events.size()];
        if(events.size() > 0){
            for(int i=0;i<events.size();i++){
                collisonSet[collisonSet.length-1] = events.get(i).getEndTime();
                collisonSet[collisonSet.length-1] = events.get(i).getEndDate();
            }
            emptyList_txt.setVisibility(View.INVISIBLE);
            if(adapter != null){
                adapter.clear();
            }
            adapter = new EventsListAdapter(this,events);
            listView = (ListView) findViewById(R.id.events_listview);
            listView.setAdapter(adapter);
        }else{
            emptyList_txt.setVisibility(View.VISIBLE);
        }

    }
}
