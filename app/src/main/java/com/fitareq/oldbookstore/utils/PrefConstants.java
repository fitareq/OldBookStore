package com.fitareq.oldbookstore.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefConstants {
    private static final String MY_PREF_NAME = "OLD_BOOK_STORE";
    private static SharedPreferences.Editor editor = null;
    private static SharedPreferences prefs = null;

    public static final String KEY_ACCESS_TOKEN = "ACCESS_TOKEN";


    public static void saveStringToSharedPref(String key,String value, Context context) {
        if (editor == null)
            editor = context.getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStringFromSharedPref(String key, Context context) {
        if (prefs == null)
            prefs = context.getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE);
       return prefs.getString(key, null);
    }

    public static boolean isUserLoggedIn(Context context){
        if (prefs == null)
            prefs = context.getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE);

        return prefs.getBoolean(AppConstants.IS_USER_LOGGED_IN, false);
    }
    public static void setUserLoggedIn(Context context, boolean value){
        if (editor == null)
            editor = context.getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE).edit();

       editor.putBoolean(AppConstants.IS_USER_LOGGED_IN, value);
    }
}
