package com.fitareq.oldbookstore.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fitareq.oldbookstore.data.model.homepage_books.Data;
import com.fitareq.oldbookstore.data.model.responses.ApiResponse;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.network.Api;
import com.fitareq.oldbookstore.data.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRepository {
    private ApiService apiService;
    private MutableLiveData<RepositoryResponse<Data>> _allBooks;
    private LiveData<RepositoryResponse<Data>> allBooks;

    public HomeRepository() {
        apiService = Api.getInstance().getApiService();
        _allBooks = new MutableLiveData<>();
        allBooks = _allBooks;
    }

    public LiveData<RepositoryResponse<Data>> getAllBooks() {
        _allBooks.postValue(RepositoryResponse.loading());
        Call<ApiResponse<Data>> call = apiService.getAllBooks();
        call.enqueue(new Callback<ApiResponse<Data>>() {
            @Override
            public void onResponse(Call<ApiResponse<Data>> call, Response<ApiResponse<Data>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _allBooks.postValue(RepositoryResponse.success(response.body().getMessage(), response.body().getData()));
                } else _allBooks.postValue(RepositoryResponse.error(response.message()));
            }

            @Override
            public void onFailure(Call<ApiResponse<Data>> call, Throwable t) {
                _allBooks.postValue(RepositoryResponse.error(t.getMessage()));
            }
        });


        return allBooks;
    }
}
