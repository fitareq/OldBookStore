package com.fitareq.oldbookstore.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fitareq.oldbookstore.data.model.homepage_books.Item;
import com.fitareq.oldbookstore.data.model.responses.ApiResponse;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.model.single_category.SingleCategoryData;
import com.fitareq.oldbookstore.data.network.Api;
import com.fitareq.oldbookstore.data.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepository {
    private ApiService apiService;

    private MutableLiveData<RepositoryResponse<List<Item>>> _searchedData;
    private LiveData<RepositoryResponse<List<Item>>> searchedData;

    public SearchRepository(Application application) {
        apiService = Api.getInstance(application).getApiService();
        _searchedData = new MutableLiveData<>();
        searchedData = _searchedData;
    }

    public LiveData<RepositoryResponse<List<Item>>> getSearchedProduct(String query) {

        _searchedData.postValue(RepositoryResponse.loading());

        Call<ApiResponse<List<Item>>> call = apiService.getSearchedProduct(query);
        call.enqueue(new Callback<ApiResponse<List<Item>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Item>>> call, Response<ApiResponse<List<Item>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _searchedData.postValue(RepositoryResponse.success(response.body().getMessage(), response.body().getData()));
                } else _searchedData.postValue(RepositoryResponse.error(response.message()));
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Item>>> call, Throwable t) {
                _searchedData.postValue(RepositoryResponse.error(t.getMessage()));
            }
        });

        return searchedData;
    }


}
