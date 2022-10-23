package com.fitareq.oldbookstore.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.fitareq.oldbookstore.R;
import com.fitareq.oldbookstore.data.model.item.Item;
import com.fitareq.oldbookstore.databinding.ActivityHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        List<Item> items = new ArrayList<>();

        binding.itemListRv.setLayoutManager(new LinearLayoutManager(this));
        binding.itemListRv.setAdapter(new ItemsAdapter(items));
    }
}