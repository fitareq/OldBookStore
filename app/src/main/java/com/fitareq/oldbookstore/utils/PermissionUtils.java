package com.fitareq.oldbookstore.utils;

import static com.fitareq.oldbookstore.utils.AppConstants.REQUEST_PERMISSION_LOCATION;
import static com.fitareq.oldbookstore.utils.AppConstants.REQUEST_PERMISSION_STORAGE;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtils {
    public static void askForStoragePermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, REQUEST_PERMISSION_STORAGE);
    }

    public static void askForLocationPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, REQUEST_PERMISSION_LOCATION);
    }

    public static boolean isStoragePermissionGranted(Activity activity) {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isLocationPermissionGranted(Activity activity) {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

    }
}
