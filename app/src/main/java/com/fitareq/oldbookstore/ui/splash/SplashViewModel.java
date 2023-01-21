package com.fitareq.oldbookstore.ui.splash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fitareq.oldbookstore.data.model.profile.UserProfileData;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.repository.ProfileRepository;

public class SplashViewModel extends AndroidViewModel {
    private ProfileRepository repository;
    public SplashViewModel(@NonNull Application application) {
        super(application);
        repository = new ProfileRepository(application);
    }

    public LiveData<RepositoryResponse<UserProfileData>> getUserProfileInfo(){
        return repository.getUserProfileInfo();
    }
}
