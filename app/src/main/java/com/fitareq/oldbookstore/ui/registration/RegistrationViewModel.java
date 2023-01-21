package com.fitareq.oldbookstore.ui.registration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fitareq.oldbookstore.data.model.UpdateTokenBody;
import com.fitareq.oldbookstore.data.model.registration.RegistrationBody;
import com.fitareq.oldbookstore.data.model.registration.RegistrationResponse;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.repository.FirebaseTokenUpdateRepository;
import com.fitareq.oldbookstore.data.repository.RegistrationRepository;

public class RegistrationViewModel extends AndroidViewModel {
    private RegistrationRepository repository;
    private FirebaseTokenUpdateRepository firebaseTokenUpdateRepository;
    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        repository = new RegistrationRepository(application);
        firebaseTokenUpdateRepository = new FirebaseTokenUpdateRepository(application);
    }

    public LiveData<RepositoryResponse<RegistrationResponse>> registerUser(RegistrationBody registrationBody){
        return repository.registerUser(registrationBody);
    }

    public LiveData<RepositoryResponse<Object>> updateToken(UpdateTokenBody body){
        return firebaseTokenUpdateRepository.tokenUpdate(body);
    }
}
