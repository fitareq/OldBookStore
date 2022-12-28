package com.fitareq.oldbookstore.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fitareq.oldbookstore.data.model.profile.UserProfileData;
import com.fitareq.oldbookstore.data.model.registration.RegistrationBody;
import com.fitareq.oldbookstore.data.model.registration.RegistrationResponse;
import com.fitareq.oldbookstore.data.model.responses.ApiResponse;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.network.Api;
import com.fitareq.oldbookstore.data.network.ApiService;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {
    private ApiService apiService;
    private MutableLiveData<RepositoryResponse<UserProfileData>> _userProfileResponse;
    private LiveData<RepositoryResponse<UserProfileData>> userProfileResponse;

    private MutableLiveData<RepositoryResponse<String>> _logoutResponse;
    private LiveData<RepositoryResponse<String>> logoutResponse;

    public ProfileRepository(Application application) {
        apiService = Api.getInstance(application).getApiService();
        _userProfileResponse = new MutableLiveData<>();
        userProfileResponse = _userProfileResponse;

        _logoutResponse = new MutableLiveData<>();
        logoutResponse = _logoutResponse;
    }

    public LiveData<RepositoryResponse<UserProfileData>> getUserProfileInfo(){
        _userProfileResponse.postValue(RepositoryResponse.loading());


        Call<ApiResponse<UserProfileData>> call = apiService.getUserProfile();
        call.enqueue(new Callback<ApiResponse<UserProfileData>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserProfileData>> call, Response<ApiResponse<UserProfileData>> response) {
                if (response.isSuccessful() && response.body() != null){
                    _userProfileResponse.postValue(RepositoryResponse.success(response.body().getMessage(), response.body().getData()));
                }else _userProfileResponse.postValue(RepositoryResponse.error(response.message()));
            }

            @Override
            public void onFailure(Call<ApiResponse<UserProfileData>> call, Throwable t) {
                _userProfileResponse.postValue(RepositoryResponse.error(t.getMessage()));
            }
        });

        return userProfileResponse;
    }
}
