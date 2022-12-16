package com.fitareq.oldbookstore.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.fitareq.oldbookstore.R;

@SuppressLint("InflateParams")
public class CustomDialog {
    private Activity activity;
    private AlertDialog dialog;
    private View view;
    private AlertDialog.Builder builder;


    // constructor of dialog class
    // with parameter activity

    public CustomDialog(Activity myActivity) {
        activity = myActivity;
        // adding ALERT Dialog builder object and passing activity as parameter
        builder = new AlertDialog.Builder(activity);
    }

    // dismiss method
    public void dismissDialog() {
        dialog.dismiss();
    }

    public void loading() {
        if (dialog != null)
            dismissDialog();
        setView(activity.getLayoutInflater().inflate(R.layout.loading_layout, null));
        dialog.show();
    }

    public void success() {
        dismissDialog();
        setView(activity.getLayoutInflater().inflate(R.layout.success_layout, null));
        dialog.show();
    }

    public void error(String msg) {
        dismissDialog();
        setView(activity.getLayoutInflater().inflate(R.layout.error_layout, null));
        TextView textView = view.findViewById(R.id.error_message);
        if (msg == null || msg.isEmpty())
            msg = activity.getString(R.string.something_went_wrong);
        textView.setText(msg);

        view.findViewById(R.id.close).setOnClickListener(view1 -> dismissDialog());
        dialog.show();
    }

    private void setView(View view) {
        this.view = view;
        builder.setView(view);
        builder.setCancelable(false);
        dialog = builder.create();
    }
}
