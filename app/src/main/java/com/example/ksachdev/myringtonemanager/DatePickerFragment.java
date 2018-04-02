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
        Tools tools = new Tools();
        if(dateText != null){
            DateFormatSymbols dateSymbols = new DateFormatSymbols();
            String month_text = dateSymbols.getMonths()[month].substring(0,3);
            dateText.setText(month_text + ' ' + tools.getDatePrefix(dayOfMonth) + " , " + year);
        }
    }


}
