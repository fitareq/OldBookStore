package com.fitareq.oldbookstore.utils;

import com.fitareq.oldbookstore.BuildConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AppConstants {
    public static final String BASE_URL = "https://oldbookstore.topnewsbd.live/api/v1/";
    public static final String BOOK_DETAILS_ENDPOINT = BASE_URL+"books/";
    public static final String SINGLE_CATEGORY_BOOKS_ENDPOINT = BASE_URL+"category-books/";
    public static final String ACCEPT_ORDER_ENDPOINT = BASE_URL+"accept-order/";
    public static final String IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN";
    public static String TOKEN = "";

    public static final String DATE_FORMAT = "hh:mmaa  MMM dd,yyyy";

    public static final int REQUEST_IMAGE = 101;
    public static final int REQUEST_PERMISSION_STORAGE = 200;
    public static final int REQUEST_PERMISSION_LOCATION = 100;
    public static final String KEY_PROVIDER_AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";
    public static final String KEY_ORDER_TYPE = "ORDER_TYPE";
    public static final String VALUE_ORDER_TYPE_BUY = "ORDER_TYPE_BUY";
    public static final String VALUE_ORDER_TYPE_SELL = "ORDER_TYPE_SELL";


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
