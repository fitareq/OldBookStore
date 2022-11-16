package com.fitareq.oldbookstore.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.fitareq.oldbookstore.R;
import com.fitareq.oldbookstore.data.model.login.LoginBody;
import com.fitareq.oldbookstore.data.repository.LoginRepository;
import com.fitareq.oldbookstore.databinding.ActivityLoginBinding;
import com.fitareq.oldbookstore.ui.MainActivity;
import com.fitareq.oldbookstore.ui.registration.RegistrationActivity;
import com.fitareq.oldbookstore.utils.CustomDialog;
import com.fitareq.oldbookstore.utils.PrefConstants;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;
    private CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        dialog = new CustomDialog(LoginActivity.this);

        

        binding.loginBtn.setOnClickListener(view -> userLogin());
        binding.registerTv.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegistrationActivity.class)));
    }

    private void userLogin() {
        String email = binding.emailIdEt.getText().toString();
        String password = binding.passwordEt.getText().toString();

        if (TextUtils.isEmpty(email)){
            binding.emailIdLayout.setError("Enter email");
            return;
        }
        if (TextUtils.isEmpty(password)){
            //binding.passwordEt.setError("Enter password");
            binding.passwordLayout.setError("Enter Password");
            return;
        }else if (password.length() < 6){
            binding.passwordLayout.setError(getString(R.string.password_length));
            return;
        }

        binding.loginBtn.setEnabled(false);

        dialog.loading();

        viewModel.userLogin(new LoginBody(email, password), new LoginRepository.LoginCallBack() {
            @Override
            public void onSuccess(String token) {
                //Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                PrefConstants.saveStringToSharedPref(PrefConstants.KEY_ACCESS_TOKEN, token, LoginActivity.this);
                PrefConstants.setUserLoggedIn(LoginActivity.this, true);
                binding.loginBtn.setEnabled(true);
                dialog.success();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                },500);

            }

            @Override
            public void onFailure(String errorMessage) {
                //Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                binding.loginBtn.setEnabled(true);
                dialog.error(errorMessage);
            }
        });
    }
}