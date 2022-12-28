package com.fitareq.oldbookstore.data.repository;

import android.app.Application;

import com.fitareq.oldbookstore.data.network.Api;
import com.fitareq.oldbookstore.data.network.ApiService;

public class ProfileSetupRepository {
    private ApiService apiService;
    public ProfileSetupRepository(Application application) {
        apiService = Api.getInstance(application).getApiService();
    }
}
