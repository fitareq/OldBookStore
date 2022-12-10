package com.fitareq.oldbookstore.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fitareq.oldbookstore.data.model.login.LoginBody;
import com.fitareq.oldbookstore.data.model.login.LoginResponse;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.repository.LoginRepository;

public class LoginViewModel extends AndroidViewModel {
    private LoginRepository repository;
    public LiveData<RepositoryResponse<LoginResponse>> loginResponse;
    private MutableLiveData<RepositoryResponse<LoginResponse>> _loginResponse;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new LoginRepository();
        _loginResponse = new MutableLiveData<>();
        loginResponse = _loginResponse;
    }

    public void userLogin(LoginBody body) {
        _loginResponse.postValue(repository.userLogin(body).getValue());
    }


}
