package com.example.ksachdev.myringtonemanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by ksachdev on 2018-03-31.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    TextView dateText;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dateText = (TextView) getActivity().findViewById(getArguments().getInt("view"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        return new DatePickerDialog(getActivity(),this,calendar.get(calendar.YEAR),calendar.get(calendar.MONTH),
                calendar.get(calendar.DATE));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


        if(dateText != null){
            DateFormatSymbols dateSymbols = new DateFormatSymbols();
            String month_text = dateSymbols.getMonths()[month];

            String day_str = "";
            if(dayOfMonth > 3 && dayOfMonth < 31){
                day_str = dayOfMonth + "th";
            }else if(dayOfMonth == 31 || dayOfMonth == 1){
                day_str = dayOfMonth + "st";
            }else if(dayOfMonth == 2){
                day_str = dayOfMonth + "nd";
            }else if(dayOfMonth == 3){
                day_str = dayOfMonth + "rd";
            }

            dateText.setText(month_text + ' ' + day_str + ", " + year);
        }
    }
}
