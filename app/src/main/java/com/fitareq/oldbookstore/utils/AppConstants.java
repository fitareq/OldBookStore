package com.fitareq.oldbookstore.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AppConstants {
    public static final String BASE_URL = "https://oldbookstore.topnewsbd.live/api/v1/";
    public static final String IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN";

    public static final String DATE_FORMAT = "hh:mmaa  MMM dd,yyyy";


    public static String getDate(long milliSeconds)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendar.getTimeInMillis());
        return formatter.format(calendar.getTime());
    }
}
