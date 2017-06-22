package com.opensooq.weatherapp.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by a7mad on 6/19/2017.
 */

public class DateTimeUtil {

    private DateTimeUtil() {
        /*to hide implicit public constructor*/
    }

    public static String convertStringToDate(String dateString) {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat24 = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("MMM dd,EEE");
        try {
            Date date = inputFormat24.parse(dateString);
            return outputFormatAmPm.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDayNameFromDateString(String dateString) {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat inputFormat24 = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("EEEE");
        try {
            Date date = inputFormat24.parse(dateString);
            return outputFormatAmPm.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

}
