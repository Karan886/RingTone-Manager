package com.example.ksachdev.myringtonemanager;

import android.app.DialogFragment;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ScheduleEventsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Tools tools = new Tools();
    DatabaseHelper db;
    private static final String FIELD_IS_EMPTY_MESSAGE = "This Field cannot be Left Empty";
    private static final String DATE_IS_INVALID_MESSAGE = "The current Date chosen is invalid";
    private static  final String TIME_IS_INVALID_MESSAGE = "The Current Time chosen is invalid";
    private static final String TAG = "EventScheduleActivity";

    private String startSpinnerValue = "silent";
    private String endSpinnerValue = "vibrate";
    private Spinner startSpinner;
    private Spinner endSpinner;


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

        startSpinner = (Spinner) findViewById(R.id.spinner_start);
        startSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> startSpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.ringtoneModes_start,
                android.R.layout.simple_spinner_dropdown_item);
        startSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startSpinner.setAdapter(startSpinnerAdapter);


        endSpinner = (Spinner) findViewById(R.id.spinner_end);
        endSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> endSpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.ringtoneModes_end,
                android.R.layout.simple_spinner_dropdown_item);
        endSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endSpinner.setAdapter(endSpinnerAdapter);


        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText title = (EditText) findViewById(R.id.EventTitle);
                EditText desc = (EditText) findViewById(R.id.description_field);

                if(validateForm() == true){
                    Event mEvent = new Event(title.getText().toString(),desc.getText().toString(),
                            startDateText.getText().toString(),startTimeText.getText().toString(),
                            endDateText.getText().toString(),endTimeText.getText().toString(),0);
                    db = DatabaseHelper.getInstance(getApplicationContext());

                    db.addEvents(mEvent,new String[]{startSpinnerValue,endSpinnerValue});
                    gotoMain();
                }
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view,int pos, long id){
        if(view.getId() == R.id.spinner_start){
            startSpinnerValue = parent.getItemAtPosition(pos).toString();
        }else{
            endSpinnerValue = parent.getItemAtPosition(pos).toString();
        }
    }

    public void onNothingSelected(AdapterView<?> parent){
        endSpinnerValue = "vibrate";
        startSpinnerValue = "silent";
    }

    private void gotoMain(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        Button startTime = (Button) findViewById(R.id.StartTime_Button);
        Button endTime = (Button) findViewById(R.id.EndTime_Button);

        if(tools.isDatePassed(startDate.getText().toString())){
            startDate.setError(DATE_IS_INVALID_MESSAGE);
            Log.i(TAG,DATE_IS_INVALID_MESSAGE);
            return false;
        }
        if(tools.isDateBefore(endDate.getText().toString(),startDate.getText().toString())){
            endDate.setError(DATE_IS_INVALID_MESSAGE);
            Log.i(TAG,DATE_IS_INVALID_MESSAGE);
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        String currentTime = calendar.get(calendar.HOUR_OF_DAY) + ":" + calendar.get(calendar.MINUTE);
        if(tools.isTimePassed(startTime.getText().toString(),currentTime)){
            startTime.setError(TIME_IS_INVALID_MESSAGE);
            Log.i(TAG,TIME_IS_INVALID_MESSAGE);
            return false;
        }

        if(tools.isTimePassed(endTime.getText().toString(),startTime.getText().toString())){
            endTime.setError(TIME_IS_INVALID_MESSAGE);
            Log.i(TAG,TIME_IS_INVALID_MESSAGE);
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

        return cal.get(cal.HOUR_OF_DAY) + ":" + cal.get(cal.MINUTE);
    }
}
