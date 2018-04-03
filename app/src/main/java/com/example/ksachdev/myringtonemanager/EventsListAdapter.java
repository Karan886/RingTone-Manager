package com.example.ksachdev.myringtonemanager;

import android.content.Context;
import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

/**
 * Created by ksachdev on 2018-04-01.
 */

public class EventsListAdapter extends ArrayAdapter<Event> {

    private static final String TAG = "EventsListAdapter";

    public EventsListAdapter(@NonNull Context context, @NonNull List<Event> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.events_list_layout,parent,false);
        }

        Event event = getItem(position);

        TextView row_title = (TextView) convertView.findViewById(R.id.list_title);
        row_title.setText(event.getTitle());

        TextView row_startDate = (TextView) convertView.findViewById(R.id.list_date);
        row_startDate.setText(event.getStartDate());

        TextView row_time = (TextView) convertView.findViewById(R.id.list_time);
        row_time.setText(event.getStartTime() + " - " + event.getEndTime());

        ImageView imgView = (ImageView) convertView.findViewById(R.id.list_img);
        Tools tools = new Tools();

        Date mDate = tools.getDate(event.getEndDate(),event.getEndTime());
        Date cDate = new Date();

        if(cDate.before(mDate)){
            imgView.setImageResource(R.drawable.circe_list_item);
        }

        return convertView;
    }
}
