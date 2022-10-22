package com.fitareq.oldbookstore.ui.profile_setup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.fitareq.oldbookstore.databinding.ActivityProfileSetupBinding;

public class ProfileSetupActivity extends AppCompatActivity {
    private ActivityProfileSetupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileSetupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}