package com.fitareq.oldbookstore.ui.single_category;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fitareq.oldbookstore.data.model.registration.RegistrationBody;
import com.fitareq.oldbookstore.data.model.registration.RegistrationResponse;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.model.single_category.SingleCategoryData;
import com.fitareq.oldbookstore.data.repository.RegistrationRepository;
import com.fitareq.oldbookstore.data.repository.SingleCategoryRepository;

public class SingleCategoryViewModel extends AndroidViewModel {
    private SingleCategoryRepository repository;
    public SingleCategoryViewModel(@NonNull Application application) {
        super(application);
        repository = new SingleCategoryRepository();
    }

    public LiveData<RepositoryResponse<SingleCategoryData>> getCategoryBooks(String url) {
        return repository.getCategoryBooks(url);
    }
}
