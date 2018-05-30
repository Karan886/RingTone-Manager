package com.example.ksachdev.myringtonemanager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ksachdev on 2018-04-02.
 */

public class AlarmReciever extends BroadcastReceiver {
    private static String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        String mode = intent.getStringExtra("mode");
        DatabaseHelper db = DatabaseHelper.getInstance(context);

        String msg = "";

       if(mode != null){
           if(mode.equals("end")){
               msg = "Ending Silent Event";
               String key = intent.getStringExtra("key");
               db.updateEvent(key);
               am.setRingerMode(2);
           }else if(mode.equals("start")){
               msg = "Starting Silent Event";
               am.setRingerMode(0);
           }

       }
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();

    }

}
