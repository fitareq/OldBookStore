package com.fitareq.oldbookstore.data.network;

import com.fitareq.oldbookstore.data.model.login.LoginBody;
import com.fitareq.oldbookstore.data.model.login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("user/login")
    Call<LoginResponse> userLogin(LoginBody loginBody);
}
