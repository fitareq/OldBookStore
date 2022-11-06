package com.fitareq.oldbookstore.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.fitareq.oldbookstore.R;

public class CustomDialog {
    private Activity activity;
    private AlertDialog dialog;

    // constructor of dialog class
    // with parameter activity
    public CustomDialog(Activity myActivity) {
        activity = myActivity;
    }

    @SuppressLint("InflateParams")
    public void startLoadingDialog() {

        // adding ALERT Dialog builder object and passing activity as parameter
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // layoutinflater object and use activity to get layout inflater
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_layout, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    // dismiss method
   public void dismissDialog() {
        dialog.dismiss();
    }
}
