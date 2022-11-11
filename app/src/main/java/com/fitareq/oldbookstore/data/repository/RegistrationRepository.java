package com.fitareq.oldbookstore.data.repository;

import com.fitareq.oldbookstore.data.model.registration.RegistrationBody;
import com.fitareq.oldbookstore.data.model.registration.RegistrationResponse;
import com.fitareq.oldbookstore.data.network.ApiService;
import com.fitareq.oldbookstore.data.network.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationRepository {
    private ApiService apiService;

    public RegistrationRepository() {
        apiService = Api.getInstance().getApiService();
    }

    public void registerUser(RegistrationBody registrationBody, RegistrationCallBack callBack){
        Call<RegistrationResponse> call = apiService.userRegistration(registrationBody);
        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    callBack.onSuccess(response.body());
                }else callBack.onFailed(response.message());
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                callBack.onFailed(t.getMessage());
            }
        });
    }

    public interface RegistrationCallBack{
        void onSuccess(RegistrationResponse registrationResponse);
        void onFailed(String message);
    }
}
