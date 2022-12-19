package com.fitareq.oldbookstore.ui.registration;

import static com.fitareq.oldbookstore.utils.AppConstants.KEY_PROVIDER_AUTHORITY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.fitareq.oldbookstore.R;
import com.fitareq.oldbookstore.data.model.registration.RegistrationBody;
import com.fitareq.oldbookstore.databinding.ActivityRegistrationBinding;
import com.fitareq.oldbookstore.ui.MainActivity;
import com.fitareq.oldbookstore.utils.AppConstants;
import com.fitareq.oldbookstore.utils.CustomDialog;
import com.fitareq.oldbookstore.utils.PrefConstants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.ByteArrayOutputStream;
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

    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //askForPermission();

        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        dialog = new CustomDialog(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        binding.registerIllustrator.setClipToOutline(true);

        //sfindLocation();

        binding.continueBtn.setOnClickListener(view -> {
            registerUser();
        });
        binding.addressEt.setOnClickListener(view -> {
            askForLocationPermission();
            getCurrentLocation();
            //findLocation();
        });
        binding.registerIllustrator.setOnClickListener(view -> {
            selectImage();
        });
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED
        ) {
            askForLocationPermission();

        } else {
            findLocation();
        }
    }

    private void selectImage() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
        ) {
            askForStoragePermission();
        } else {
            getImageFromStorage();
        }
    }

    private void getImageFromStorage() {
       /* Matisse.from(RegistrationActivity.this)
                .choose(MimeType.ofAll(), false)
                .countable(false)
                .maxSelectable(1)
                .imageEngine(new PicassoEngine()).capture(true)
                .captureStrategy(new CaptureStrategy(false, KEY_PROVIDER_AUTHORITY))
                .forResult(AppConstants.REQUEST_IMAGE);*/


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

    private void askForStoragePermission() {
        ActivityCompat.requestPermissions(RegistrationActivity.this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 200);
    }

    private void findLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {

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
        } else {
            askForLocationPermission();
        }
    }

    private void askForLocationPermission() {
        ActivityCompat.requestPermissions(RegistrationActivity.this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, 100);
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
        if (requestCode == 100) {

            if (grantResults.length > 0) {
                if (ContextCompat.checkSelfPermission(RegistrationActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(RegistrationActivity.this,
                                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    findLocation();
                } else askForLocationPermission();
            } else askForLocationPermission();
        } else if (requestCode == 200) {
            if (grantResults.length > 0) {
                if (ContextCompat.checkSelfPermission(RegistrationActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(RegistrationActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    getImageFromStorage();
                } else askForStoragePermission();
            } else askForStoragePermission();
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
                compressImageUnder(imageUri);


                //binding.registerIllustrator.setImageBitmap(bitmap);
            }
        }
    }

    private void compressImageUnder(Uri imageUri) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bitmap;
                    ContentResolver contentResolver = getContentResolver();
                    if (Build.VERSION.SDK_INT < 28) {
                        bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri);
                    } else {
                        ImageDecoder.Source source = ImageDecoder.createSource(contentResolver, imageUri);
                        bitmap = ImageDecoder.decodeBitmap(source);
                    }
                    file = new File(RegistrationActivity.this.getCacheDir(), "profile_image.jpg");
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.registerIllustrator.setPadding(0,0,0,0);
                            Picasso.with(RegistrationActivity.this).load(file).fit().into(binding.registerIllustrator);
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