package com.fitareq.oldbookstore.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fitareq.oldbookstore.data.model.homepage_books.Item;
import com.fitareq.oldbookstore.data.model.responses.ApiResponse;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.network.Api;
import com.fitareq.oldbookstore.data.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailsRepository {
    private ApiService apiService;

    private MutableLiveData<RepositoryResponse<Item>> _bookDetails;
    private LiveData<RepositoryResponse<Item>> bookDetails;

    public BookDetailsRepository() {
        apiService = Api.getInstance().getApiService();
        _bookDetails = new MutableLiveData<>();
        bookDetails = _bookDetails;
    }

    public LiveData<RepositoryResponse<Item>> getBookDetails(String url) {

        _bookDetails.postValue(RepositoryResponse.loading());

        Call<ApiResponse<Item>> call = apiService.getBookDetails(url);
        call.enqueue(new Callback<ApiResponse<Item>>() {
            @Override
            public void onResponse(Call<ApiResponse<Item>> call, Response<ApiResponse<Item>> response) {
                if (response.isSuccessful() && response.body() != null){
                    _bookDetails.postValue(RepositoryResponse.success(response.body().getMessage(), response.body().getData()));
                }else _bookDetails.postValue(RepositoryResponse.error(response.message()));
            }

            @Override
            public void onFailure(Call<ApiResponse<Item>> call, Throwable t) {
                _bookDetails.postValue(RepositoryResponse.error(t.getMessage()));
            }
        });

        return bookDetails;
    }

}
