package com.fitareq.oldbookstore.data.repository;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

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

public class AddBookRepository {
    private ApiService apiService;
    private MutableLiveData<RepositoryResponse<AddBookResponse>> _addBookResponse;
    private LiveData<RepositoryResponse<AddBookResponse>> addBookResponse;

    private MutableLiveData<RepositoryResponse<List<Category>>> _categories;
    private LiveData<RepositoryResponse<List<Category>>> categories;

    public AddBookRepository(Application application) {
        apiService = Api.getInstance(application).getApiService();
        _addBookResponse = new MutableLiveData<>();
        addBookResponse = _addBookResponse;
        _categories = new MutableLiveData<>();
        categories = _categories;

    }

    public LiveData<RepositoryResponse<AddBookResponse>> addBook(AddBookBody body) {
        _addBookResponse.postValue(RepositoryResponse.loading());

        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), body.getTitle());
        RequestBody authorName = RequestBody.create(MediaType.parse("text/plain"), body.getAuthorName());
        RequestBody categoryId = RequestBody.create(MediaType.parse("text/plain"), body.getCategoryId());
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), body.getDescription());
        RequestBody quantity = RequestBody.create(MediaType.parse("text/plain"), body.getQuantity());
        RequestBody price = RequestBody.create(MediaType.parse("text/plain"), body.getPrice());
        MultipartBody.Part[] image = new MultipartBody.Part[body.getImage().size()];

        int i = 0;
        for (File file : body.getImage()) {
            RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), file);
            image[i] = MultipartBody.Part.createFormData(
                    "image["+i+"]",
                    file.getName(),
                    imageBody
            );
            ++i;
        }

//        Call<ApiResponse<String>> call = apiService.addPostTest(title, authorName, categoryId, description, quantity, price, image);

/*call.enqueue(new Callback<ApiResponse<String>>() {
    @Override
    public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
        String res = response.body().toString();
        Log.v("tag", res);
    }

    @Override
    public void onFailure(Call<ApiResponse<String>> call, Throwable t) {

    }
});*/
        Call<ApiResponse<AddBookResponse>> call = apiService.addPost(title, authorName, categoryId, description, quantity, price, image);
        call.enqueue(new Callback<ApiResponse<AddBookResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<AddBookResponse>> call, Response<ApiResponse<AddBookResponse>> response) {

                String res = response.toString();
                ApiResponse<AddBookResponse> responseBody = response.body();
                if (response.isSuccessful() && responseBody != null) {
                    if (responseBody.getStatus().equalsIgnoreCase("Success")) {
                        _addBookResponse.postValue(RepositoryResponse.success(responseBody.getMessage(), responseBody.getData()));
                    }
                } else {
                    _addBookResponse.postValue(RepositoryResponse.error(response.message()));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<AddBookResponse>> call, Throwable t) {
                _addBookResponse.postValue(RepositoryResponse.error(t.getMessage()));
            }
        });


        return addBookResponse;
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
