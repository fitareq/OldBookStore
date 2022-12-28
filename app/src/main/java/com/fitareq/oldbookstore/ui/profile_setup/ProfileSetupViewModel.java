package com.fitareq.oldbookstore.ui.profile_setup;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.fitareq.oldbookstore.data.repository.ProfileSetupRepository;

public class ProfileSetupViewModel extends AndroidViewModel {
    private ProfileSetupRepository repository;
    public ProfileSetupViewModel(@NonNull Application application) {
        super(application);
        repository = new ProfileSetupRepository(application);
    }
}
