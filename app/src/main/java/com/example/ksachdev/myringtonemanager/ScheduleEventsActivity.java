package com.example.ksachdev.myringtonemanager;

import android.app.DialogFragment;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ScheduleEventsActivity extends AppCompatActivity {

    Tools tools = new Tools();
    DatabaseHelper db;
    private static final String FIELD_IS_EMPTY_MESSAGE = "This Field cannot be Left Empty";
    private static final String DATE_IS_INVALID_MESSAGE = "The current Date chosen is invalid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_events);

        final TextView startDateText = (TextView) findViewById(R.id.startDate_text);
        startDateText.setText(getFormattedDate());

        final TextView endDateText = (TextView) findViewById(R.id.EndDate_text);
        endDateText.setText(getFormattedDate());

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

        final Button startTimeText = (Button) findViewById(R.id.StartTime_Button);
        startTimeText.setText(getFormattedCurrentTime());
        startTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(R.id.StartTime_Button);
            }
        });

        final Button endTimeText = (Button) findViewById(R.id.EndTime_Button);
        endTimeText.setText(getFormattedCurrentTime());
        endTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(R.id.EndTime_Button);
            }
        });

        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText title = (EditText) findViewById(R.id.EventTitle);
                EditText desc = (EditText) findViewById(R.id.description_field);

                if(validateForm() == true){
                    Event mEvent = new Event(title.getText().toString(),desc.getText().toString(),
                            startDateText.getText().toString(),startTimeText.getText().toString(),
                            endDateText.getText().toString(),endTimeText.getText().toString());
                    db = DatabaseHelper.getInstance(getApplicationContext());
                    db.addEvents(mEvent);
                    gotoMain();
                }

            }
        });
    }

    private void gotoMain(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public boolean validateForm(){
        EditText title = (EditText) findViewById(R.id.EventTitle);
        if(TextUtils.isEmpty(title.getText().toString())){
            title.setError(FIELD_IS_EMPTY_MESSAGE);
            return false;
        }
        EditText desc = (EditText) findViewById(R.id.description_field);
        if(TextUtils.isEmpty(desc.getText().toString())){
            desc.setError(FIELD_IS_EMPTY_MESSAGE);
            return false;
        }

        TextView startDate = (TextView) findViewById(R.id.startDate_text);
        TextView endDate = (TextView) findViewById(R.id.EndDate_text);

        if(tools.isDatePassed(startDate.getText().toString()) == false){
            startDate.setError(DATE_IS_INVALID_MESSAGE);
            return false;
        }
        if(tools.isDateBefore(startDate.getText().toString(),endDate.getText().toString()) == false){
            endDate.setError(DATE_IS_INVALID_MESSAGE);
            return false;
        }
        return true;
    }

    public void showDatePicker(int resourceID){
        Bundle bundle = new Bundle();
        bundle.putInt("view",resourceID);

        DialogFragment fragment = new DatePickerFragment();
        fragment.setArguments(bundle);
        fragment.show(getFragmentManager(),"Date Picker");
    }

    public void showTimePicker(int resourceID){
        Bundle bundle = new Bundle();
        bundle.putInt("view",resourceID);

        DialogFragment fragment = new TimePickerFragment();
        fragment.setArguments(bundle);
        fragment.show(getFragmentManager(),"Time Picker");
    }

    public String getFormattedDate(){
        String result = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        DateFormatSymbols dateSymbols = new DateFormatSymbols();
        String month = dateSymbols.getMonths()[cal.get(cal.MONTH)].substring(0,3);

        result = month + " " + tools.getDatePrefix(cal.get(cal.DATE)) + " , " + cal.get(cal.YEAR);
        return result;
    }

    public String getFormattedCurrentTime(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        return tools.getFormattedTime(cal.get(cal.HOUR_OF_DAY),cal.get(cal.MINUTE));
    }
}
