package com.fitareq.oldbookstore.ui.search;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fitareq.oldbookstore.data.model.homepage_books.Item;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.model.single_category.SingleCategoryData;
import com.fitareq.oldbookstore.data.repository.SearchRepository;
import com.fitareq.oldbookstore.data.repository.SingleCategoryRepository;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {
    private SearchRepository repository;
    public SearchViewModel(@NonNull Application application) {
        super(application);
        repository = new SearchRepository();
    }

    public LiveData<RepositoryResponse<List<Item>>> getSearchedProduct(String query)  {
        return repository.getSearchedProduct(query);
    }
}
