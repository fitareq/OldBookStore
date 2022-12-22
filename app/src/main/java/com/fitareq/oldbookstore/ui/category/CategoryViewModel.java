package com.fitareq.oldbookstore.ui.category;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fitareq.oldbookstore.data.model.add_book.Category;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.repository.CategoryRepository;
import com.fitareq.oldbookstore.data.repository.HomeRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private CategoryRepository repository;
    public CategoryViewModel(@NonNull Application application) {
        super(application);
        repository = new CategoryRepository();
    }

    public LiveData<RepositoryResponse<List<Category>>> getCategories() {
        return repository.getCategories();
    }
}
