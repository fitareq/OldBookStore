package com.fitareq.oldbookstore.ui.registration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.fitareq.oldbookstore.data.repository.RegistrationRepository;

public class RegistrationViewModel extends AndroidViewModel {
    private RegistrationRepository repository;
    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        repository = new RegistrationRepository();
    }
}
