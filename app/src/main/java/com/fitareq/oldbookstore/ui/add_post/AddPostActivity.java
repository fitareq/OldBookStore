package com.fitareq.oldbookstore.ui.add_post;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.fitareq.oldbookstore.databinding.ActivityAddPostBinding;

public class AddPostActivity extends AppCompatActivity {

    private ActivityAddPostBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}