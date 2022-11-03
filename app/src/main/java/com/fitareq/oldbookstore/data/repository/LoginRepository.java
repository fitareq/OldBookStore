package com.fitareq.oldbookstore.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fitareq.oldbookstore.data.model.login.LoginBody;
import com.fitareq.oldbookstore.data.model.login.LoginResponse;
import com.fitareq.oldbookstore.data.network.ApiService;
import com.fitareq.oldbookstore.data.network.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private ApiService apiService;
    private MutableLiveData<LoginResponse> _loginResponse;
    public LoginRepository() {
        apiService = Api.getInstance().getApiService();
        _loginResponse = new MutableLiveData<>();
    }

    public MutableLiveData<LoginResponse> userLogin(LoginBody body){
        Call<LoginResponse> call = apiService.userLogin(body);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    _loginResponse.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {

            }
        });

        return _loginResponse;
    }

}
