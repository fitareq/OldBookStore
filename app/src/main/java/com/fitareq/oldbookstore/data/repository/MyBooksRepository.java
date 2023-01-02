package com.fitareq.oldbookstore.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fitareq.oldbookstore.data.model.add_book.Category;
import com.fitareq.oldbookstore.data.model.my_books.MyBooksResponse;
import com.fitareq.oldbookstore.data.model.responses.ApiResponse;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.network.Api;
import com.fitareq.oldbookstore.data.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBooksRepository {
    private ApiService apiService;

    private MutableLiveData<RepositoryResponse<MyBooksResponse>> _myBooks;
    private LiveData<RepositoryResponse<MyBooksResponse>> myBooks;

    public MyBooksRepository(Application application) {
        apiService = Api.getInstance(application).getApiService();
        _myBooks = new MutableLiveData<>();
        myBooks = _myBooks;

    }

    public LiveData<RepositoryResponse<MyBooksResponse>> getMyBooks(String url) {
        _myBooks.postValue(RepositoryResponse.loading());
        Call<ApiResponse<MyBooksResponse>> call = apiService.getMyBooks(url);
        call.enqueue(new Callback<ApiResponse<MyBooksResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<MyBooksResponse>> call, Response<ApiResponse<MyBooksResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _myBooks.postValue(RepositoryResponse.success(response.message(), response.body().getData()));
                } else _myBooks.postValue(RepositoryResponse.error(response.message()));
            }

            @Override
            public void onFailure(Call<ApiResponse<MyBooksResponse>> call, Throwable t) {
                _myBooks.postValue(RepositoryResponse.error(t.getMessage()));
            }
        });
        return myBooks;
    }
}
