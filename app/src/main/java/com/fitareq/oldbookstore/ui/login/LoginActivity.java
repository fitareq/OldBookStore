package com.fitareq.oldbookstore.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.fitareq.oldbookstore.data.model.login.LoginBody;
import com.fitareq.oldbookstore.data.repository.LoginRepository;
import com.fitareq.oldbookstore.databinding.ActivityLoginBinding;
import com.fitareq.oldbookstore.ui.MainActivity;
import com.fitareq.oldbookstore.utils.CustomDialog;
import com.fitareq.oldbookstore.utils.PrefConstants;

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
            binding.emailIdEt.setError("Enter email");
            return;
        }
        if (TextUtils.isEmpty(password)){
            binding.emailIdEt.setError("Enter password");
            return;
        }else if (password.length() < 6){
            binding.emailIdEt.setError("Password must be at least 6 character");
            return;
        }

        binding.loginBtn.setEnabled(false);
        CustomDialog dialog = new CustomDialog(LoginActivity.this);
        dialog.startLoadingDialog();

        viewModel.userLogin(new LoginBody(email, password), new LoginRepository.LoginCallBack() {
            @Override
            public void onSuccess(String token) {
                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                PrefConstants.saveStringToSharedPref(PrefConstants.KEY_ACCESS_TOKEN, token, LoginActivity.this);
                binding.loginBtn.setEnabled(true);
                dialog.dismissDialog();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                binding.loginBtn.setEnabled(true);
                dialog.dismissDialog();
            }
        });
    }
}