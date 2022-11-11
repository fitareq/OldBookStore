package com.fitareq.oldbookstore.ui.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.fitareq.oldbookstore.R;
import com.fitareq.oldbookstore.data.model.registration.RegistrationBody;
import com.fitareq.oldbookstore.data.model.registration.RegistrationResponse;
import com.fitareq.oldbookstore.data.repository.RegistrationRepository;
import com.fitareq.oldbookstore.databinding.ActivityRegistrationBinding;
import com.fitareq.oldbookstore.ui.login.LoginActivity;
import com.fitareq.oldbookstore.utils.CustomDialog;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;
    private RegistrationViewModel viewModel;
    private String longitude, latitude;
    private CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);

        dialog =new CustomDialog(this);

        longitude = "123456";
        latitude = "123456";

        binding.continueBtn.setOnClickListener(view -> {
            registerUser();
        });
    }

    private void registerUser() {
        String email = binding.emailIdEt.getText().toString();
        String fullName = binding.fullNameEt.getText().toString();
        String mobile = binding.mobileEt.getText().toString();
        String password = binding.passwordEt.getText().toString();
        String confirmPassword = binding.confirmPasswordEt.getText().toString();
        String address = binding.addressEt.getText().toString();

        if (TextUtils.isEmpty(email)){
            binding.emailIdLayout.setError(getString(R.string.enter_mail));
            return;
        }
        if (TextUtils.isEmpty(fullName)){
            binding.fullNameLayout.setError(getString(R.string.enter_name));
            return;
        }if (TextUtils.isEmpty(mobile)){
            binding.mobileLayout.setError(getString(R.string.enter_mobile));
            return;
        }if (TextUtils.isEmpty(password)){
            binding.passwordLayout.setError(getString(R.string.enter_pass));
            return;
        }if (password.length() < 6){
            binding.emailIdLayout.setError(getString(R.string.password_length));
            return;
        }if (!password.equals(confirmPassword)){
            binding.emailIdLayout.setError(getString(R.string.password_didnt_match));
            return;
        }if (TextUtils.isEmpty(address)){
            binding.emailIdLayout.setError(getString(R.string.enter_address));
            return;
        }
        RegistrationBody body = new RegistrationBody(fullName, email, mobile, password, address, latitude, longitude);
        viewModel.registerUser(body, new RegistrationRepository.RegistrationCallBack() {
            @Override
            public void onSuccess(RegistrationResponse registrationResponse) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                finish();
            }

            @Override
            public void onFailed(String message) {

            }
        });

    }
}