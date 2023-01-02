package com.fitareq.oldbookstore.ui.my_books;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fitareq.oldbookstore.data.model.add_book.Category;
import com.fitareq.oldbookstore.data.model.my_books.MyBooksResponse;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.repository.CategoryRepository;
import com.fitareq.oldbookstore.data.repository.MyBooksRepository;

import java.util.List;

public class MyBooksViewModel extends AndroidViewModel {
    private MyBooksRepository repository;

    public MyBooksViewModel(@NonNull Application application) {
        super(application);
        repository = new MyBooksRepository(application);
    }

    public LiveData<RepositoryResponse<MyBooksResponse>> getMyBooks(String url) {
        return repository.getMyBooks(url);
    }
}
