package com.fitareq.oldbookstore.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fitareq.oldbookstore.data.model.add_book.AddBookBody;
import com.fitareq.oldbookstore.data.model.add_book.AddBookResponse;
import com.fitareq.oldbookstore.data.model.add_book.Category;
import com.fitareq.oldbookstore.data.model.responses.ApiResponse;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.network.Api;
import com.fitareq.oldbookstore.data.network.ApiService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private ApiService apiService;

    private MutableLiveData<RepositoryResponse<List<Category>>> _categories;
    private LiveData<RepositoryResponse<List<Category>>> categories;

    public CategoryRepository() {
        apiService = Api.getInstance().getApiService();
        _categories = new MutableLiveData<>();
        categories = _categories;

    }
    public LiveData<RepositoryResponse<List<Category>>> getCategories(){
        _categories.postValue(RepositoryResponse.loading());
        Call<ApiResponse<List<Category>>> call = apiService.getAllCategory();
        call.enqueue(new Callback<ApiResponse<List<Category>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Category>>> call, Response<ApiResponse<List<Category>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _categories.postValue(RepositoryResponse.success(response.message(), response.body().getData()));
                }else _categories.postValue(RepositoryResponse.error(response.message()));
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Category>>> call, Throwable t) {
                _categories.postValue(RepositoryResponse.error(t.getMessage()));
            }
        });
        return categories;
    }
}
