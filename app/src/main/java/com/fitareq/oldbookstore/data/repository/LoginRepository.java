package com.fitareq.oldbookstore.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fitareq.oldbookstore.data.model.login.LoginBody;
import com.fitareq.oldbookstore.data.model.login.LoginResponse;
import com.fitareq.oldbookstore.data.model.responses.ApiResponse;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.network.ApiService;
import com.fitareq.oldbookstore.data.network.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private ApiService apiService;
    private MutableLiveData<RepositoryResponse<LoginResponse>> _loginResponse;
    private LiveData<RepositoryResponse<LoginResponse>> loginResponse;

    public LoginRepository() {
        apiService = Api.getInstance().getApiService();
        _loginResponse = new MutableLiveData<>();
        loginResponse = _loginResponse;
    }

    public LiveData<RepositoryResponse<LoginResponse>> userLogin(LoginBody body) {
        _loginResponse.postValue(RepositoryResponse.loading());

        Call<ApiResponse<LoginResponse>> call = apiService.userLogin(body);

        call.enqueue(new Callback<ApiResponse<LoginResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<LoginResponse>> call, Response<ApiResponse<LoginResponse>> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        _loginResponse.postValue(RepositoryResponse.success(response.body().getMessage(), response.body().getData()));
                    } else _loginResponse.postValue(RepositoryResponse.error(response.message()));
                } catch (Exception e) {
                    _loginResponse.postValue(RepositoryResponse.error(e.getMessage()));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<LoginResponse>> call, Throwable t) {
                _loginResponse.postValue(RepositoryResponse.error(t.getMessage()));
            }
        });


        return loginResponse;
    }
}
