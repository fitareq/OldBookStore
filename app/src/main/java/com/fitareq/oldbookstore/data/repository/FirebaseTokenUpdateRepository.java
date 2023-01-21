package com.fitareq.oldbookstore.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fitareq.oldbookstore.data.model.UpdateTokenBody;
import com.fitareq.oldbookstore.data.model.responses.ApiResponse;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.network.Api;
import com.fitareq.oldbookstore.data.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirebaseTokenUpdateRepository {
    private ApiService apiService;

    private MutableLiveData<RepositoryResponse<Object>> _tokenUpdate;
    private LiveData<RepositoryResponse<Object>> tokenUpdate;

    public FirebaseTokenUpdateRepository(Application application) {
        apiService = Api.getInstance(application).getApiService();
        _tokenUpdate = new MutableLiveData<>();
        tokenUpdate = _tokenUpdate;
    }

    public LiveData<RepositoryResponse<Object>> tokenUpdate(UpdateTokenBody body) {
        _tokenUpdate.postValue(RepositoryResponse.loading());

        Call<ApiResponse<Object>> call = apiService.updateToken(body);
        call.enqueue(new Callback<ApiResponse<Object>>() {
            @Override
            public void onResponse(Call<ApiResponse<Object>> call, Response<ApiResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _tokenUpdate.postValue(RepositoryResponse.success(response.message(), response.body().getData()));
                } else _tokenUpdate.postValue(RepositoryResponse.error(response.message()));
            }

            @Override
            public void onFailure(Call<ApiResponse<Object>> call, Throwable t) {
                _tokenUpdate.postValue(RepositoryResponse.error(t.getMessage()));
            }
        });
        return tokenUpdate;
    }
}
