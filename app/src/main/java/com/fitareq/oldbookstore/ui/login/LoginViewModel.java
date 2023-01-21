package com.fitareq.oldbookstore.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fitareq.oldbookstore.data.model.UpdateTokenBody;
import com.fitareq.oldbookstore.data.model.login.LoginBody;
import com.fitareq.oldbookstore.data.model.login.LoginResponse;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.repository.FirebaseTokenUpdateRepository;
import com.fitareq.oldbookstore.data.repository.LoginRepository;

public class LoginViewModel extends AndroidViewModel {
    private LoginRepository repository;
    private FirebaseTokenUpdateRepository firebaseTokenUpdateRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new LoginRepository(application);
        firebaseTokenUpdateRepository = new FirebaseTokenUpdateRepository(application);
    }

    public LiveData<RepositoryResponse<LoginResponse>> userLogin(LoginBody body) {
        return repository.userLogin(body);
    }

    public LiveData<RepositoryResponse<Object>> updateToken(UpdateTokenBody body){
        return firebaseTokenUpdateRepository.tokenUpdate(body);
    }

}
