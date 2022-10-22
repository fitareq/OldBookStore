package com.fitareq.oldbookstore.data.network;

import com.fitareq.oldbookstore.data.model.login.Login;

import retrofit2.http.GET;

public interface ApiService {
    @GET("login")
    public void userLogin(Login login);
}
