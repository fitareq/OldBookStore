package com.fitareq.oldbookstore.ui.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fitareq.oldbookstore.data.model.profile.UserProfileData;
import com.fitareq.oldbookstore.data.model.registration.RegistrationBody;
import com.fitareq.oldbookstore.data.model.registration.RegistrationResponse;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.repository.ProfileRepository;
import com.fitareq.oldbookstore.data.repository.RegistrationRepository;

public class ProfileViewModel extends AndroidViewModel {
    private ProfileRepository repository;
    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repository = new ProfileRepository();
    }

    public LiveData<RepositoryResponse<UserProfileData>> getUserProfileInfo(){
        return repository.getUserProfileInfo();
    }
}
