package com.example.ksachdev.myringtonemanager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by ksachdev on 2018-04-02.
 */

public class AlarmReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        String mode = intent.getStringExtra("mode");

        String msg = "";

        if(mode.equals("end")){
            msg = "Ending Silent Event";
            am.setRingerMode(2);
        }else if(mode.equals("start")){
            msg = "Starting Silent Event";
            am.setRingerMode(0);
        }

        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();

    }

    /*public void buildNotification(Context context,String title, int requestId, String msg_small){

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setAutoCancel(true);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title).setContentText(msg_small);

        Intent intent = new Intent(context,MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,requestId,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);

        NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(requestId,mBuilder.build());
    }*/
}
