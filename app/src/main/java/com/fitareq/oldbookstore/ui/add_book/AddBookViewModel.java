package com.fitareq.oldbookstore.ui.add_book;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fitareq.oldbookstore.data.model.add_book.AddBookBody;
import com.fitareq.oldbookstore.data.model.add_book.AddBookResponse;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.repository.AddBookRepository;

public class AddBookViewModel extends AndroidViewModel {
    private AddBookRepository repository;
    public LiveData<RepositoryResponse<AddBookResponse>> addBookResponse;
    private final MutableLiveData<RepositoryResponse<AddBookResponse>> _addBookResponse;

    public AddBookViewModel(@NonNull Application application) {
        super(application);
        repository = new AddBookRepository();
        _addBookResponse = new MutableLiveData<>();
        addBookResponse = _addBookResponse;
    }

    public void addBook(AddBookBody body) {
        _addBookResponse.postValue(repository.addBook(body).getValue());
    }

}
