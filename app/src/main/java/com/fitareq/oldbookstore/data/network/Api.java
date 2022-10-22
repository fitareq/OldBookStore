package com.fitareq.oldbookstore.data.network;

import com.fitareq.oldbookstore.utils.AppConstants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    private static Api instance = null;
    private final Retrofit retrofit;

    private Api(){
        retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Api getInstance(){
        if (instance == null)
            instance = new Api();
        return instance;
    }

    public ApiService getApiService(){
        return retrofit.create(ApiService.class);
    }
}
