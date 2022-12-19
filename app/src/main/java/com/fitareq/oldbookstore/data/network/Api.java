package com.fitareq.oldbookstore.data.network;

import com.fitareq.oldbookstore.utils.AppConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
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
