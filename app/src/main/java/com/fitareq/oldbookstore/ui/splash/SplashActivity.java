package com.fitareq.oldbookstore.ui.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.fitareq.oldbookstore.data.model.Status;
import com.fitareq.oldbookstore.databinding.ActivitySplashBinding;
import com.fitareq.oldbookstore.ui.MainActivity;
import com.fitareq.oldbookstore.ui.login.LoginActivity;
import com.fitareq.oldbookstore.utils.AppConstants;
import com.fitareq.oldbookstore.utils.PrefConstants;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private SplashViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        boolean isLoggedIn = PrefConstants.isUserLoggedIn(this);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (isLoggedIn){
                FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Log.v("firebasetoken", s);
                        AppConstants.FIREBASE_TOKEN = s;
                    }
                });
                /*viewModel.getUserProfileInfo().observe(SplashActivity.this, userInfo->{
                    if (userInfo.getStatus() == Status.SUCCESS) {
                        AppConstants.USER_ID = userInfo.getData().getId().toString();
                    }
                });*/
                AppConstants.TOKEN = PrefConstants.getStringFromSharedPref(PrefConstants.KEY_ACCESS_TOKEN, this);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }else {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
            finish();
        },1000);
    }
}