package com.fitareq.oldbookstore.ui.registration;

import static com.fitareq.oldbookstore.utils.AppConstants.KEY_PROVIDER_AUTHORITY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;

import com.fitareq.oldbookstore.R;
import com.fitareq.oldbookstore.data.model.registration.RegistrationBody;
import com.fitareq.oldbookstore.databinding.ActivityRegistrationBinding;
import com.fitareq.oldbookstore.ui.MainActivity;
import com.fitareq.oldbookstore.utils.AppConstants;
import com.fitareq.oldbookstore.utils.CustomDialog;
import com.fitareq.oldbookstore.utils.ImageUtils;
import com.fitareq.oldbookstore.utils.PermissionUtils;
import com.fitareq.oldbookstore.utils.PrefConstants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;
    private RegistrationViewModel viewModel;
    private String longitude, latitude, address;
    private CustomDialog dialog;
    private File file = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //askForPermission();

        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        dialog = new CustomDialog(this);
        binding.registerIllustrator.setClipToOutline(true);

        binding.continueBtn.setOnClickListener(view -> {
            registerUser();
        });
        binding.addressEt.setOnClickListener(view -> {
            getCurrentLocation();
        });
        binding.registerIllustrator.setOnClickListener(view -> {
            selectImage();
        });
    }

    private void getCurrentLocation() {
        if (PermissionUtils.isLocationPermissionGranted(this)
        ) {
            findLocation();

        } else {
            PermissionUtils.askForLocationPermission(this);
        }
    }

    private void selectImage() {
        if (PermissionUtils.isStoragePermissionGranted(this)) {
            getImageFromStorage();
        } else {
            PermissionUtils.askForStoragePermission(this);
        }
    }

    private void getImageFromStorage() {
        Matisse.from(this)
                .choose(MimeType.ofImage(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(new CaptureStrategy(false, KEY_PROVIDER_AUTHORITY))
                .maxSelectable(1)
                .imageEngine(new PicassoEngine())
                .showPreview(false)
                .forResult(AppConstants.REQUEST_IMAGE);
    }

    @SuppressLint("MissingPermission")
    private void findLocation() {
        FusedLocationProviderClient
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                dialog.loading();
                Geocoder geocoder = new Geocoder(RegistrationActivity.this, Locale.getDefault());
                try {
                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    Address addrs = addressList.get(0);
                    latitude = String.valueOf(addrs.getLatitude());
                    longitude = String.valueOf(addrs.getLongitude());
                    address = addrs.getAddressLine(0);
                    binding.addressEt.setText(address);
                    dialog.dismissDialog();
                } catch (IOException e) {
                    e.printStackTrace();
                    dialog.error("Failed to get current location!!");
                }
            }
        });
    }


    private void registerUser() {
        String email = binding.emailIdEt.getText().toString();
        String fullName = binding.fullNameEt.getText().toString();
        String mobile = binding.mobileEt.getText().toString();
        String password = binding.passwordEt.getText().toString();
        String confirmPassword = binding.confirmPasswordEt.getText().toString();
        String address = binding.addressEt.getText().toString();

        if (TextUtils.isEmpty(email)) {
            binding.emailIdLayout.setError(getString(R.string.enter_mail));
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailIdLayout.setError(getString(R.string.enter_valid_mail));
            return;
        }
        if (TextUtils.isEmpty(fullName)) {
            binding.fullNameLayout.setError(getString(R.string.enter_name));
            return;
        }
        if (TextUtils.isEmpty(mobile)) {
            binding.mobileLayout.setError(getString(R.string.enter_mobile));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            binding.passwordLayout.setError(getString(R.string.enter_pass));
            return;
        }
        if (password.length() < 6) {
            binding.emailIdLayout.setError(getString(R.string.password_length));
            return;
        }
        if (!password.equals(confirmPassword)) {
            binding.emailIdLayout.setError(getString(R.string.password_didnt_match));
            return;
        }
        if (TextUtils.isEmpty(address)) {
            binding.emailIdLayout.setError(getString(R.string.enter_address));
            return;
        }
        dialog.loading();
        RegistrationBody body = new RegistrationBody(fullName, email, mobile, password, address, latitude, longitude, file);

        viewModel.registerUser(body).observe(this, registrationResponse -> {
            switch (registrationResponse.getStatus()) {
                case LOADING:
                    dialog.loading();
                    break;
                case SUCCESS:
                    PrefConstants.saveStringToSharedPref(PrefConstants.KEY_ACCESS_TOKEN, registrationResponse.getData().getAccessToken(), RegistrationActivity.this);
                    PrefConstants.setUserLoggedIn(RegistrationActivity.this, true);
                    AppConstants.TOKEN = PrefConstants.getStringFromSharedPref(PrefConstants.KEY_ACCESS_TOKEN, this);
                    dialog.success();
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                            finish();
                        }
                    }, 500);
                    break;
                case FAILED:
                    dialog.error(registrationResponse.getMessage());
                    break;
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == AppConstants.REQUEST_PERMISSION_LOCATION) {

            if (grantResults.length > 0) {
                if (PermissionUtils.isLocationPermissionGranted(this)) {
                    findLocation();
                } else PermissionUtils.askForLocationPermission(this);
            } else PermissionUtils.askForLocationPermission(this);
        } else if (requestCode == AppConstants.REQUEST_PERMISSION_STORAGE) {
            if (grantResults.length > 0) {
                if (PermissionUtils.isStoragePermissionGranted(this)) {
                    getImageFromStorage();
                } else PermissionUtils.askForStoragePermission(this);
            } else PermissionUtils.askForStoragePermission(this);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppConstants.REQUEST_IMAGE) {
            //List<Uri> uri;
            if (data != null) {
                dialog.loading();
                Uri imageUri = Matisse.obtainResult(data).get(0);
                compressImageQualityIntoHalf(imageUri);


                //binding.registerIllustrator.setImageBitmap(bitmap);
            }
        }
    }

    private void compressImageQualityIntoHalf(Uri imageUri) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    file = ImageUtils.compressImageQualityIntoHalf(imageUri, RegistrationActivity.this, "profile_image");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.registerIllustrator.setPadding(0, 0, 0, 0);
                            Picasso.with(RegistrationActivity.this).load(file).memoryPolicy(MemoryPolicy.NO_CACHE).fit().into(binding.registerIllustrator);
                            dialog.dismissDialog();
                        }
                    });

                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.error(e.getMessage());
                        }
                    });

                }

            }
        }).start();

    }
}