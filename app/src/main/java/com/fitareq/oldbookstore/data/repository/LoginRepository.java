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
    public LoginRepository() {
        apiService = Api.getInstance().getApiService();
    }

    public void userLogin(LoginBody body, LoginCallBack loginCallBack){
        Call<LoginResponse> call = apiService.userLogin(body);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    loginCallBack.onSuccess(response.body().getAccessToken());
                }else loginCallBack.onFailure(response.message());
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                loginCallBack.onFailure(t.getMessage());
            }
        });
    }


    public interface LoginCallBack{
        void onSuccess(String token);
        void onFailure(String errorMessage);
    }
}
