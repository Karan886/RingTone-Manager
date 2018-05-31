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
        String action = intent.getStringExtra("action");
        DatabaseHelper db = DatabaseHelper.getInstance(context);

        String msg = "";
        String initAction = "silent";

       if(mode != null && action != null){
           if(mode.equals("end")){
               msg = "Ending "+initAction+" Event";
               String key = intent.getStringExtra("key");
               db.updateEvent(key);
               if(action.equals("vibrate")){
                   am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
               }else{
                   am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
               }
           }else if(mode.equals("start")){
               msg = "Starting "+action+" Event";
               initAction = action;
               if(action.equals("silent")){
                   am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
               }else{
                   am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
               }
           }

       }
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();


    }

}
