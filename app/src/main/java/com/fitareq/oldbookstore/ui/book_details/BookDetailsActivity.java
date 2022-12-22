package com.fitareq.oldbookstore.ui.book_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.fitareq.oldbookstore.R;
import com.fitareq.oldbookstore.data.model.homepage_books.Item;
import com.fitareq.oldbookstore.databinding.ActivityBookDetailsBinding;
import com.fitareq.oldbookstore.utils.CustomDialog;

import java.util.List;

public class BookDetailsActivity extends AppCompatActivity {
    private ActivityBookDetailsBinding binding;
    private BookDetailsViewModel viewModel;
    private CustomDialog dialog;
    private ImageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(BookDetailsViewModel.class);
        dialog = new CustomDialog(this);
        String url = "https://oldbookstore.topnewsbd.live/api/v1/books/"+getIntent().getStringExtra("id");

        setSupportActionBar(binding.toolbarLay.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Details");
        binding.toolbarLay.toolbar.getNavigationIcon().setTint(Color.parseColor("#ffffff"));


        viewModel.getBookDetails(url).observe(this, bookDetails->{
            if (bookDetails !=null){
                switch (bookDetails.getStatus()){
                    case LOADING:
                        dialog.loading();
                        break;
                    case SUCCESS:
                        setupBookDetails(bookDetails.getData());
                        dialog.dismissDialog();
                        break;
                    case FAILED:
                        dialog.error("Something went wrong!!");
                        break;
                }
            }
        });


        binding.toolbarLay.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setupBookDetails(Item data) {
        setupBookImages(data.getImages());
        binding.bookTitle.setText(data.getTitle());
        binding.bookAuthor.setText(data.getAuthorName());
        binding.bookPrice.setText(data.getPrice());
        binding.bookQty.setText(data.getQty());
        binding.bookDescription.setText(data.getDescription());

    }

    private void setupBookImages(List<String> images) {
        adapter = new ImageAdapter(images);
        binding.bookImages.setAdapter(adapter);
        binding.bookImages.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                String current = String.valueOf(binding.bookImages.getCurrentItem()+1);
                String total =String.valueOf(adapter.getItemCount());
                binding.imagesCount.setText(getString(R.string.images_count,current, total));
            }
        });

    }
}