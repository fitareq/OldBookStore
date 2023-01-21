package com.fitareq.oldbookstore.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fitareq.oldbookstore.data.model.book_details.CreateBookOrderBody;
import com.fitareq.oldbookstore.data.model.book_details.CreateBookOrderResponse;
import com.fitareq.oldbookstore.data.model.book_details.UpdateBookBody;
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

    private MutableLiveData<RepositoryResponse<CreateBookOrderResponse>> _orderResponse;
    private LiveData<RepositoryResponse<CreateBookOrderResponse>> orderResponse;

    private MutableLiveData<RepositoryResponse<Object>> _deleteBook;
    private LiveData<RepositoryResponse<Object>> deleteBook;

    private MutableLiveData<RepositoryResponse<Object>> _updateBook;
    private LiveData<RepositoryResponse<Object>> updateBook;

    public BookDetailsRepository(Application application) {
        apiService = Api.getInstance(application).getApiService();
        _bookDetails = new MutableLiveData<>();
        bookDetails = _bookDetails;
        _orderResponse = new MutableLiveData<>();
        orderResponse = _orderResponse;

        _deleteBook = new MutableLiveData<>();
        deleteBook = _deleteBook;

        _updateBook = new MutableLiveData<>();
        updateBook = _updateBook;

    }

    public LiveData<RepositoryResponse<Item>> getBookDetails(String url) {

        _bookDetails.postValue(RepositoryResponse.loading());

        Call<ApiResponse<Item>> call = apiService.getBookDetails(url);
        call.enqueue(new Callback<ApiResponse<Item>>() {
            @Override
            public void onResponse(Call<ApiResponse<Item>> call, Response<ApiResponse<Item>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _bookDetails.postValue(RepositoryResponse.success(response.body().getMessage(), response.body().getData()));
                } else _bookDetails.postValue(RepositoryResponse.error(response.message()));
            }

            @Override
            public void onFailure(Call<ApiResponse<Item>> call, Throwable t) {
                _bookDetails.postValue(RepositoryResponse.error(t.getMessage()));
            }
        });

        return bookDetails;
    }

    public LiveData<RepositoryResponse<CreateBookOrderResponse>> createOrder(CreateBookOrderBody body) {
        _orderResponse.postValue(RepositoryResponse.loading());
        Call<ApiResponse<CreateBookOrderResponse>> call = apiService.createBookOrder(body);
        call.enqueue(new Callback<ApiResponse<CreateBookOrderResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<CreateBookOrderResponse>> call, Response<ApiResponse<CreateBookOrderResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                      _orderResponse.postValue(RepositoryResponse.success(response.body().getMessage(), response.body().getData()));
                } else _orderResponse.postValue(RepositoryResponse.error(response.message()));
            }

            @Override
            public void onFailure(Call<ApiResponse<CreateBookOrderResponse>> call, Throwable t) {
                _orderResponse.postValue(RepositoryResponse.error(t.getMessage()));
            }
        });
        return orderResponse;
    }


    public LiveData<RepositoryResponse<Object>> deleteBook(String url) {

        _deleteBook.postValue(RepositoryResponse.loading());

        Call<ApiResponse<Object>> call = apiService.deleteBook(url);
        call.enqueue(new Callback<ApiResponse<Object>>() {
            @Override
            public void onResponse(Call<ApiResponse<Object>> call, Response<ApiResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _deleteBook.postValue(RepositoryResponse.success(response.body().getMessage(), response.body().getData()));
                } else _deleteBook.postValue(RepositoryResponse.error(response.message()));
            }

            @Override
            public void onFailure(Call<ApiResponse<Object>> call, Throwable t) {
                _deleteBook.postValue(RepositoryResponse.error(t.getMessage()));
            }
        });

        return deleteBook;
    }

    public LiveData<RepositoryResponse<Object>> updateBook(String url, UpdateBookBody body) {

        _updateBook.postValue(RepositoryResponse.loading());

        Call<ApiResponse<Object>> call = apiService.updateBook(url, body);
        call.enqueue(new Callback<ApiResponse<Object>>() {
            @Override
            public void onResponse(Call<ApiResponse<Object>> call, Response<ApiResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _updateBook.postValue(RepositoryResponse.success(response.body().getMessage(), response.body().getData()));
                } else _updateBook.postValue(RepositoryResponse.error(response.message()));
            }

            @Override
            public void onFailure(Call<ApiResponse<Object>> call, Throwable t) {
                _updateBook.postValue(RepositoryResponse.error(t.getMessage()));
            }
        });

        return updateBook;
    }
}
