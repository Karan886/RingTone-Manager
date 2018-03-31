package com.example.ksachdev.myringtonemanager;

import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ScheduleEventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_events);

        ImageButton startDateBtn = (ImageButton)findViewById(R.id.startDate_icon);
        startDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              showDatePicker(R.id.startDate_text);
            }
        });

        ImageButton endDatebtn = (ImageButton) findViewById(R.id.EndDate_icon);
        endDatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(R.id.EndDate_text);
            }
        });
    }

    public void showDatePicker(int resourceID){
        Bundle bundle = new Bundle();
        bundle.putInt("view",resourceID);

        DialogFragment fragment = new DatePickerFragment();
        fragment.setArguments(bundle);
        fragment.show(getFragmentManager(),"Date Picker");
    }
}
