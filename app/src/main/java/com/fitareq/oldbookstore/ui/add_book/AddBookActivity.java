package com.fitareq.oldbookstore.ui.add_book;

import static com.fitareq.oldbookstore.utils.AppConstants.KEY_PROVIDER_AUTHORITY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.fitareq.oldbookstore.databinding.ActivityAddPostBinding;
import com.fitareq.oldbookstore.ui.registration.RegistrationActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new CustomDialog(this);
        viewModel = new ViewModelProvider(this).get(AddBookViewModel.class);

        binding.imageRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new AddImageAdapter(new ArrayList<>());
        binding.imageRv.setAdapter(adapter);

        viewModel.addBookResponse.observe(this, response -> {
            switch (response.getStatus()) {
                case LOADING:
                    dialog.loading();
                    break;
                case SUCCESS:
                    dialog.success();
                    break;
                case FAILED:
                    dialog.error("An error occurred!!");
                    break;
            }
        });

        binding.addPhoto.setOnClickListener(view -> {
            PermissionUtils.askForStoragePermission(this);
        });
        binding.postButton.setOnClickListener(view -> {
            ArrayList<File> images = adapter.getImages();
            if (images == null || images.isEmpty()){
                dialog.error("Please select at least 1 (one) image...");
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