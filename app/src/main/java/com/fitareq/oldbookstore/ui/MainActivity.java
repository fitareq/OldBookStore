package com.fitareq.oldbookstore.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.fitareq.oldbookstore.R;
import com.fitareq.oldbookstore.databinding.ActivityMainBinding;
import com.fitareq.oldbookstore.ui.add_book.AddBookActivity;
import com.fitareq.oldbookstore.utils.AppConstants;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.findViewById(R.id.nav_dumb).setEnabled(false);
        navController = Navigation.findNavController(MainActivity.this, R.id.main_nav_host_fragment);

        binding.saleBookFab.setOnClickListener(view -> {
            startActivity(new Intent(this, AddBookActivity.class));
        });

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_category:
                        navController.navigate(R.id.categoryFragment);
                        break;
                    case R.id.nav_home:
                        navController.navigate(R.id.homeFragment);
                        break;
                    case R.id.nav_order:
                        navController.navigate(R.id.orderFragment);
                        break;
                    case R.id.nav_profile:
                        navController.navigate(R.id.profileFragment);
                        break;
                }
                return true;
            }
        });

    }

    public void updateTitle(String title) {
        binding.toolbar.setTitle(title);
    }
}