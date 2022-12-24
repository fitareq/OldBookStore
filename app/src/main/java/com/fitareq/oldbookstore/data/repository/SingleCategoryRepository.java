package com.fitareq.oldbookstore.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fitareq.oldbookstore.data.model.book_details.CreateBookOrderBody;
import com.fitareq.oldbookstore.data.model.book_details.CreateBookOrderResponse;
import com.fitareq.oldbookstore.data.model.homepage_books.Item;
import com.fitareq.oldbookstore.data.model.responses.ApiResponse;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.model.single_category.SingleCategoryData;
import com.fitareq.oldbookstore.data.network.Api;
import com.fitareq.oldbookstore.data.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleCategoryRepository {
    private ApiService apiService;

    private MutableLiveData<RepositoryResponse<SingleCategoryData>> _categoryData;
    private LiveData<RepositoryResponse<SingleCategoryData>> categoryData;

    public SingleCategoryRepository() {
        apiService = Api.getInstance().getApiService();
        _categoryData = new MutableLiveData<>();
        categoryData = _categoryData;
    }

    public LiveData<RepositoryResponse<SingleCategoryData>> getCategoryBooks(String url) {

        _categoryData.postValue(RepositoryResponse.loading());

        Call<ApiResponse<SingleCategoryData>> call = apiService.getSingleCategoryBook(url);
        call.enqueue(new Callback<ApiResponse<SingleCategoryData>>() {
            @Override
            public void onResponse(Call<ApiResponse<SingleCategoryData>> call, Response<ApiResponse<SingleCategoryData>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _categoryData.postValue(RepositoryResponse.success(response.body().getMessage(), response.body().getData()));
                } else _categoryData.postValue(RepositoryResponse.error(response.message()));
            }

            @Override
            public void onFailure(Call<ApiResponse<SingleCategoryData>> call, Throwable t) {
                _categoryData.postValue(RepositoryResponse.error(t.getMessage()));
            }
        });

        return _categoryData;
    }


}
