package com.fitareq.oldbookstore.data.repository;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fitareq.oldbookstore.data.model.add_book.AddBookBody;
import com.fitareq.oldbookstore.data.model.add_book.AddBookResponse;
import com.fitareq.oldbookstore.data.model.responses.ApiResponse;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.network.Api;
import com.fitareq.oldbookstore.data.network.ApiService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBookRepository {
    private ApiService apiService;
    private final MutableLiveData<RepositoryResponse<AddBookResponse>> _addBookResponse;

    public AddBookRepository() {
        apiService = Api.getInstance().getApiService();
        _addBookResponse = new MutableLiveData<>();
    }

    public LiveData<RepositoryResponse<AddBookResponse>> addBook(AddBookBody body) {
        _addBookResponse.postValue(RepositoryResponse.loading());

        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), body.getTitle());
        RequestBody authorName = RequestBody.create(MediaType.parse("text/plain"), body.getAuthorName());
        RequestBody categoryId = RequestBody.create(MediaType.parse("text/plain"), body.getCategoryId());
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), body.getDescription());
        RequestBody quantity = RequestBody.create(MediaType.parse("text/plain"), body.getQuantity());
        RequestBody price = RequestBody.create(MediaType.parse("text/plain"), body.getPrice());
        MultipartBody.Part[] images = new MultipartBody.Part[body.getImage().size()];

        int i = 0;
        for (Uri fileUri : body.getImage()) {
            File file = new File(fileUri.getPath());
            RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), file);
            images[i] = MultipartBody.Part.createFormData(
                    "image",
                    file.getName(),
                    imageBody
            );
        }


        Call<ApiResponse<AddBookResponse>> call = apiService.addPost(title, authorName, categoryId, description, quantity, price, images);
        call.enqueue(new Callback<ApiResponse<AddBookResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<AddBookResponse>> call, Response<ApiResponse<AddBookResponse>> response) {
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


        return _addBookResponse;
    }
}
