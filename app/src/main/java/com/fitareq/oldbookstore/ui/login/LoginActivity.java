package com.fitareq.oldbookstore.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;

import com.fitareq.oldbookstore.data.model.login.LoginBody;
import com.fitareq.oldbookstore.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.loginBtn.setOnClickListener(view -> userLogin());
    }

    private void userLogin() {
        String email = binding.emailIdEt.getText().toString();
        String password = binding.passwordEt.getText().toString();

        if (TextUtils.isEmpty(email)){
            binding.emailIdEt.setError("Enter email...");
            return;
        }
        if (TextUtils.isEmpty(password)){
            binding.emailIdEt.setError("Enter password");
            return;
        }else if (password.length() < 6){
            binding.emailIdEt.setError("Password must be at least 6 character");
            return;
        }

        viewModel.userLogin(new LoginBody(email, password));
    }
}