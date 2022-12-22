package com.fitareq.oldbookstore.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.fitareq.oldbookstore.databinding.ActivitySplashBinding;
import com.fitareq.oldbookstore.ui.login.LoginActivity;
import com.fitareq.oldbookstore.utils.PrefConstants;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        boolean isLoggedIn = PrefConstants.isUserLoggedIn(this);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (isLoggedIn){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }else {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
            finish();
        },1000);
    }
}