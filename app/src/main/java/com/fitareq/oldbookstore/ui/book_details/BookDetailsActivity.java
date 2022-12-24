package com.fitareq.oldbookstore.ui.book_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.fitareq.oldbookstore.R;
import com.fitareq.oldbookstore.data.model.book_details.CreateBookOrderBody;
import com.fitareq.oldbookstore.data.model.homepage_books.Item;
import com.fitareq.oldbookstore.databinding.ActivityBookDetailsBinding;
import com.fitareq.oldbookstore.databinding.DialogQuantityBinding;
import com.fitareq.oldbookstore.utils.AppConstants;
import com.fitareq.oldbookstore.utils.CustomDialog;

import java.util.List;

public class BookDetailsActivity extends AppCompatActivity {
    private ActivityBookDetailsBinding binding;
    private BookDetailsViewModel viewModel;
    private CustomDialog dialog;
    private ImageAdapter adapter;
    private int availableQty;
    private String bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(BookDetailsViewModel.class);
        dialog = new CustomDialog(this);
        bookId = getIntent().getStringExtra("id");
        String url = AppConstants.BOOK_DETAILS_ENDPOINT + bookId;

        setSupportActionBar(binding.toolbarLay.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Details");
        binding.toolbarLay.toolbar.getNavigationIcon().setTint(Color.parseColor("#ffffff"));


        viewModel.getBookDetails(url).observe(this, bookDetails -> {
            if (bookDetails != null) {
                switch (bookDetails.getStatus()) {
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

        binding.buyRequest.setOnClickListener(view -> {
            showBuyDialog();
        });
    }

    private void showBuyDialog() {
        Dialog buyDialog = new Dialog(this);
        buyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DialogQuantityBinding dialogQuantityBinding = DialogQuantityBinding.inflate(LayoutInflater.from(this));
        buyDialog.setContentView(dialogQuantityBinding.getRoot());
        Window window = buyDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setDimAmount(0.8f);
            window.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }

        dialogQuantityBinding.add.setOnClickListener(view -> {
            int selectedQty = Integer.parseInt(dialogQuantityBinding.quantityInput.getText().toString());
            if (selectedQty < availableQty) {
                dialogQuantityBinding.quantityInput.setText(String.valueOf(++selectedQty));

            }
        });
        dialogQuantityBinding.subtract.setOnClickListener(view -> {
            int selectedQty = Integer.parseInt(dialogQuantityBinding.quantityInput.getText().toString());
            if (selectedQty > 1) {
                dialogQuantityBinding.quantityInput.setText(String.valueOf(--selectedQty));

            }
        });

        dialogQuantityBinding.cancel.setOnClickListener(view -> buyDialog.dismiss());
        dialogQuantityBinding.send.setOnClickListener(view -> {
            String qty = dialogQuantityBinding.quantityInput.getText().toString();
            sendBuyRequest(qty);
            buyDialog.dismiss();
        });

        buyDialog.show();
    }

    private void sendBuyRequest(String qty) {

        CreateBookOrderBody body = new CreateBookOrderBody(bookId, qty);

        viewModel.createOrder(body).observe(this, orderResponse->{
            if (orderResponse != null){
                switch (orderResponse.getStatus()){
                    case LOADING:
                        dialog.loading();
                        break;
                    case SUCCESS:
                        dialog.success();
                        new Handler(Looper.getMainLooper()).postDelayed(this::onBackPressed, 1000);
                        break;
                    case FAILED:
                        dialog.error("Couldn't process request");
                        break;
                }
            }
        });
    }

    private void setupBookDetails(Item data) {
        setupBookImages(data.getImages());
        binding.bookTitle.setText(data.getTitle());
        binding.bookAuthor.setText(getString(R.string.author, data.getAuthorName()));
        binding.bookPrice.setText(getString(R.string.price, data.getPrice()));
        availableQty = Integer.parseInt(data.getQty());
        binding.bookQty.setText(getString(R.string.available_quantity, data.getQty()));
        binding.bookDescription.setText(getString(R.string.description, data.getDescription()));

    }

    private void setupBookImages(List<String> images) {
        adapter = new ImageAdapter(images);
        binding.bookImages.setAdapter(adapter);
        binding.bookImages.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                String current = String.valueOf(binding.bookImages.getCurrentItem() + 1);
                String total = String.valueOf(adapter.getItemCount());
                binding.imagesCount.setText(getString(R.string.images_count, current, total));
            }
        });

    }
}