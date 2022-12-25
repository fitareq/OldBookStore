package com.fitareq.oldbookstore.ui.single_category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.fitareq.oldbookstore.R;
import com.fitareq.oldbookstore.data.model.homepage_books.Item;
import com.fitareq.oldbookstore.databinding.ActivitySingleCategoryBinding;
import com.fitareq.oldbookstore.ui.MainActivity;
import com.fitareq.oldbookstore.ui.home.ItemsAdapter;
import com.fitareq.oldbookstore.ui.registration.RegistrationActivity;
import com.fitareq.oldbookstore.utils.AppConstants;
import com.fitareq.oldbookstore.utils.CustomDialog;
import com.fitareq.oldbookstore.utils.PrefConstants;

import java.util.List;

public class SingleCategoryActivity extends AppCompatActivity {

    private ActivitySingleCategoryBinding binding;
    private SingleCategoryViewModel viewModel;
    private CustomDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySingleCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarLay.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Details");
        binding.toolbarLay.toolbar.getNavigationIcon().setTint(Color.parseColor("#ffffff"));


        String categoryId = getIntent().getStringExtra("id");
        String url = AppConstants.SINGLE_CATEGORY_BOOKS_ENDPOINT+categoryId;
        viewModel = new ViewModelProvider(this).get(SingleCategoryViewModel.class);
        dialog = new CustomDialog(this);

        viewModel.getCategoryBooks(url).observe(this, categoryResponse->{
            if (categoryResponse !=null){
                switch (categoryResponse.getStatus()){
                    case LOADING:
                        dialog.loading();
                        break;
                    case SUCCESS:
                        getSupportActionBar().setTitle(categoryResponse.getData().getCategoryTitle());
                        setupBooks(categoryResponse.getData().getCategoryBooks());
                        dialog.dismissDialog();
                        break;
                    case FAILED:
                        dialog.error("Unable to load data!!");
                        break;
                }
            }
        });

        binding.toolbarLay.toolbar.setNavigationOnClickListener(view -> onBackPressed());

    }

    private void setupBooks(List<Item> categoryBooks) {
        binding.booksRv.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
        binding.booksRv.setAdapter(new ItemsAdapter(categoryBooks));
    }
}