package com.fitareq.oldbookstore.ui.add_book;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fitareq.oldbookstore.data.model.add_book.AddBookBody;
import com.fitareq.oldbookstore.data.model.add_book.AddBookResponse;
import com.fitareq.oldbookstore.data.model.add_book.Category;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.repository.AddBookRepository;

import java.util.List;

public class AddBookViewModel extends AndroidViewModel {
    private AddBookRepository repository;

    public AddBookViewModel(@NonNull Application application) {
        super(application);
        repository = new AddBookRepository(application);
    }

    public LiveData<RepositoryResponse<AddBookResponse>> addBook(AddBookBody body){
        return repository.addBook(body);
    }

    public LiveData<RepositoryResponse<List<Category>>> getCategories(){
        return repository.getCategories();
    }

}
