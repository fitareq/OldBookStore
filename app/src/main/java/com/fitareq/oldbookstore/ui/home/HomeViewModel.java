package com.fitareq.oldbookstore.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fitareq.oldbookstore.data.model.homepage_books.Data;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.repository.HomeRepository;

public class HomeViewModel extends AndroidViewModel {
    private HomeRepository repository;
    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new HomeRepository();
    }

    public LiveData<RepositoryResponse<Data>> getAllBooks() {
        return repository.getAllBooks();
    }
}
