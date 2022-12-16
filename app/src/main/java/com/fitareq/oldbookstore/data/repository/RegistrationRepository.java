package com.fitareq.oldbookstore.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fitareq.oldbookstore.data.model.login.LoginResponse;
import com.fitareq.oldbookstore.data.model.registration.RegistrationBody;
import com.fitareq.oldbookstore.data.model.registration.RegistrationResponse;
import com.fitareq.oldbookstore.data.model.responses.ApiResponse;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.network.ApiService;
import com.fitareq.oldbookstore.data.network.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationRepository {
    private ApiService apiService;
    private MutableLiveData<RepositoryResponse<RegistrationResponse>> _registrationResponse;
    private LiveData<RepositoryResponse<RegistrationResponse>> registrationResponse;

    public RegistrationRepository() {
        apiService = Api.getInstance().getApiService();
        _registrationResponse = new MutableLiveData<>();
        registrationResponse = _registrationResponse;
    }

    public LiveData<RepositoryResponse<RegistrationResponse>> registerUser(RegistrationBody registrationBody){
        _registrationResponse.postValue(RepositoryResponse.loading());
        Call<ApiResponse<RegistrationResponse>> call = apiService.userRegistration(registrationBody);
        call.enqueue(new Callback<ApiResponse<RegistrationResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<RegistrationResponse>> call, Response<ApiResponse<RegistrationResponse>> response) {
                if (response.isSuccessful() && response.body() != null){
                    _registrationResponse.postValue(RepositoryResponse.success(response.body().getMessage(), response.body().getData()));
                }else _registrationResponse.postValue(RepositoryResponse.error(response.message()));
            }

            @Override
            public void onFailure(Call<ApiResponse<RegistrationResponse>> call, Throwable t) {
                _registrationResponse.postValue(RepositoryResponse.error(t.getMessage()));
            }
        });
        return registrationResponse;
    }
}
