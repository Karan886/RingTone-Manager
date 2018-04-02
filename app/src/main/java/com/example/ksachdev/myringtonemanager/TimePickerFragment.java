package com.example.ksachdev.myringtonemanager;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Created by ksachdev on 2018-03-31.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    Button timeText;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        timeText = (Button)getActivity().findViewById(getArguments().getInt("view"));
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        return new TimePickerDialog(getActivity(),this,cal.get(cal.HOUR),cal.get(cal.MINUTE)
                ,false);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Tools tools = new Tools();
        if(timeText != null){
            Log.i("hourofday",hourOfDay+"");
           timeText.setText(hourOfDay + ":" + minute);
        }
    }
}
