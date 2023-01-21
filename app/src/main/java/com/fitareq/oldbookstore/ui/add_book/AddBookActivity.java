package com.fitareq.oldbookstore.ui.add_book;

import static com.fitareq.oldbookstore.utils.AppConstants.KEY_PROVIDER_AUTHORITY;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fitareq.oldbookstore.data.model.add_book.AddBookBody;
import com.fitareq.oldbookstore.data.model.add_book.Category;
import com.fitareq.oldbookstore.databinding.ActivityAddPostBinding;
import com.fitareq.oldbookstore.utils.AppConstants;
import com.fitareq.oldbookstore.utils.CustomDialog;
import com.fitareq.oldbookstore.utils.ImageUtils;
import com.fitareq.oldbookstore.utils.PermissionUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddBookActivity extends AppCompatActivity {

    private ActivityAddPostBinding binding;
    private AddBookViewModel viewModel;
    private CustomDialog dialog;
    private int maxSelectable = 4;
    private int imageIndexCount = 0;
    public static int selectedImage = 0;
    private AddImageAdapter adapter;
    private List<Category> categoryArrayList;
    private String selectedCategoryId = "no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new CustomDialog(this);
        viewModel = new ViewModelProvider(this).get(AddBookViewModel.class);

        binding.quantityEt.setText("1");
        binding.imageRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new AddImageAdapter(new ArrayList<>());
        binding.imageRv.setAdapter(adapter);

        viewModel.getCategories().observe(this, categories -> {
            if (categories != null) {
                switch (categories.getStatus()) {
                    case LOADING:
                        dialog.loading();
                        break;
                    case SUCCESS:
                        categoryArrayList = categories.getData();
                        ArrayAdapter categoryAdapter = new ArrayAdapter(
                                AddBookActivity.this,
                                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                                categoryArrayList
                        );
                        binding.categorySpinner.setEnabled(true);
                        binding.categorySpinner.setAdapter(categoryAdapter);
                        dialog.dismissDialog();
                        break;
                    case FAILED:
                        dialog.error("Failed to load categories...");
                        binding.categorySpinner.setEnabled(false);
                        break;
                }
            }
        });


        binding.addPhoto.setOnClickListener(view -> {
            PermissionUtils.askForStoragePermission(this);
        });
        binding.addBook.setOnClickListener(view -> {
            addBook();
        });
        binding.categorySpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCategoryId = categoryArrayList.get(i).getId();
                Log.v("category", selectedCategoryId);
            }
        });

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void addBook() {
        String title = binding.postTitleEt.getText().toString();
        String author = binding.authorNameEt.getText().toString();
        int quantity = Integer.parseInt(binding.quantityEt.getText().toString());
        String price = binding.priceEt.getText().toString();
        String description = binding.descriptionEt.getText().toString();
        ArrayList<File> images = adapter.getImages();

        if (TextUtils.isEmpty(title)) {
            binding.postTitleLay.setError("Please enter a title");
            return;
        }
        if (TextUtils.isEmpty(author)) {
            binding.authorNameLay.setError("Please enter an author name");
            return;
        }
        if (selectedCategoryId.equals("no")) {
            binding.postTitleLay.setError("Please select a category");
            return;
        }
        if (quantity < 1) {
            binding.postTitleLay.setError("At least one book must be in stock");
            return;
        }
        if (TextUtils.isEmpty(price)) {
            binding.postTitleLay.setError("Please enter a valid price");
            return;
        }
        if (images == null || images.isEmpty()) {
            dialog.error("Please select at least 1 (one) image...");
            return;
        }

        AddBookBody body = new AddBookBody(title, author, selectedCategoryId, images, description, String.valueOf(quantity), price);
        viewModel.addBook(body).observe(this, addBookResponse->{
            switch (addBookResponse.getStatus()) {
                case LOADING:
                    dialog.loading();
                    break;
                case SUCCESS:
                    dialog.success();
                    onBackPressed();
                    break;
                case FAILED:
                    dialog.error("An error occurred!!");
                    break;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == AppConstants.REQUEST_PERMISSION_STORAGE) {
            if (grantResults.length > 0) {
                if (PermissionUtils.isStoragePermissionGranted(this)) {
                    getImageFromStorage();
                } else PermissionUtils.askForStoragePermission(this);
            } else PermissionUtils.askForStoragePermission(this);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void getImageFromStorage() {
        if (maxSelectable - selectedImage > 0)
            Matisse.from(this)
                    .choose(MimeType.ofImage(), false)
                    .countable(true)
                    .capture(true)
                    .captureStrategy(new CaptureStrategy(false, KEY_PROVIDER_AUTHORITY))
                    .maxSelectable(maxSelectable - selectedImage)
                    .imageEngine(new PicassoEngine())
                    .showPreview(false)
                    .forResult(AppConstants.REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppConstants.REQUEST_IMAGE) {
            //List<Uri> uri;
            if (data != null) {
                dialog.loading();
                List<Uri> imagesUri = Matisse.obtainResult(data);
                compressImagesAndSetupInRecyclerView(imagesUri);
            }
        }
    }

    private void compressImagesAndSetupInRecyclerView(List<Uri> imagesUri) {
        new Thread(() -> {
            try {
                ArrayList<File> imagesList = new ArrayList<>();
                for (Uri uri : imagesUri) {
                    File file = ImageUtils.compressImageQualityIntoHalf(uri, this, "image_" + imageIndexCount++);
                    imagesList.add(file);
                }
                runOnUiThread(() -> {
                    adapter.setImages(imagesList);
                    dialog.dismissDialog();
                    if (adapter.getItemCount() >= 4)
                        binding.addPhoto.setVisibility(View.GONE);
                    else binding.addPhoto.setVisibility(View.VISIBLE);
                });
            } catch (Exception e) {
                dialog.error("Error in selecting images, please try again...");
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        selectedImage = 0;
    }
}