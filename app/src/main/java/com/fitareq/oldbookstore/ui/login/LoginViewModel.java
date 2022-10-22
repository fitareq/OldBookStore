package com.fitareq.oldbookstore.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.fitareq.oldbookstore.data.repository.LoginRepository;

public class LoginViewModel extends AndroidViewModel {
    private LoginRepository repository;
    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new LoginRepository();
    }
}
