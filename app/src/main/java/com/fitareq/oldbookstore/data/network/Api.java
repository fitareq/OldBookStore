package com.fitareq.oldbookstore.data.network;

import android.content.Context;

import com.fitareq.oldbookstore.utils.AppConstants;
import com.fitareq.oldbookstore.utils.PrefConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    private static Api instance = null;
    private final Retrofit retrofit;

    private OkHttpClient httpClient(Context context) {
        Interceptor interceptor = chain -> {

            AppConstants.TOKEN = PrefConstants.getStringFromSharedPref(PrefConstants.KEY_ACCESS_TOKEN, context);
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

    private Api(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient(context))
                .build();
    }

    public static Api getInstance(Context context) {
        if (instance == null)
            instance = new Api(context);
        return instance;
    }

    public ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }
}
