package com.fitareq.oldbookstore.data.network;

import com.fitareq.oldbookstore.data.model.login.LoginBody;
import com.fitareq.oldbookstore.data.model.login.LoginResponse;
import com.fitareq.oldbookstore.data.model.registration.RegistrationBody;
import com.fitareq.oldbookstore.data.model.registration.RegistrationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("login")
    Call<LoginResponse> userLogin(@Body LoginBody loginBody);
    @POST("register")
    Call<RegistrationResponse> userRegistration(@Body RegistrationBody registrationBody);
}
