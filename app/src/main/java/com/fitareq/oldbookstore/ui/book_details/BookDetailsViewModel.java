package com.fitareq.oldbookstore.ui.book_details;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fitareq.oldbookstore.data.model.book_details.CreateBookOrderBody;
import com.fitareq.oldbookstore.data.model.book_details.CreateBookOrderResponse;
import com.fitareq.oldbookstore.data.model.homepage_books.Item;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.repository.BookDetailsRepository;

public class BookDetailsViewModel extends AndroidViewModel {
    private BookDetailsRepository repository;
    public BookDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = new BookDetailsRepository();
    }

    public LiveData<RepositoryResponse<Item>> getBookDetails(String url){
        return repository.getBookDetails(url);
    }

    public LiveData<RepositoryResponse<CreateBookOrderResponse>> createOrder(CreateBookOrderBody body){
        return repository.createOrder(body);
    }
}
