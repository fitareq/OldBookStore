package com.fitareq.oldbookstore.data.network;

import android.content.Context;

import com.fitareq.oldbookstore.utils.AppConstants;
import com.fitareq.oldbookstore.utils.PrefConstants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    private static Api instance = null;
    private final Retrofit retrofit;

    private OkHttpClient httpClient() {
        Interceptor interceptor = chain -> {

            String token = "Bearer " + AppConstants.TOKEN;
            Request request = chain.request().newBuilder()
                    .header("Authorization", token)
                    .build();
            return chain.proceed(request);
        };

        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }

    private Api() {
        retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient())
                .build();
    }

    public static Api getInstance() {
        if (instance == null)
            instance = new Api();
        return instance;
    }

    public ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }
}
