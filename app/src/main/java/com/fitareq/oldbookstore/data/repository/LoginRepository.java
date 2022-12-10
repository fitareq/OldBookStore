package com.fitareq.oldbookstore.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fitareq.oldbookstore.data.model.login.LoginBody;
import com.fitareq.oldbookstore.data.model.login.LoginResponse;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.network.ApiService;
import com.fitareq.oldbookstore.data.network.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private ApiService apiService;
    private MutableLiveData<RepositoryResponse<LoginResponse>> _loginResponse;

    public LoginRepository() {
        apiService = Api.getInstance().getApiService();
        _loginResponse = new MutableLiveData<>();
    }

    public MutableLiveData<RepositoryResponse<LoginResponse>> userLogin(LoginBody body) {
        _loginResponse.postValue(RepositoryResponse.loading());
        Call<LoginResponse> call = apiService.userLogin(body);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        _loginResponse.postValue(RepositoryResponse.success(response.message(), response.body()));
                    } else _loginResponse.postValue(RepositoryResponse.error(response.message()));
                } catch (Exception e) {
                    _loginResponse.postValue(RepositoryResponse.error(e.getMessage()));
                }


            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                _loginResponse.postValue(RepositoryResponse.error(t.getMessage()));
            }
        });

        return _loginResponse;
    }
}
