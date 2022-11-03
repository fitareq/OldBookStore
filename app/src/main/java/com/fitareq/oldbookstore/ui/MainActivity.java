package com.fitareq.oldbookstore.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.fitareq.oldbookstore.R;
import com.fitareq.oldbookstore.data.model.item.Item;
import com.fitareq.oldbookstore.databinding.ActivityMainBinding;
import com.fitareq.oldbookstore.ui.home.ItemsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}