package com.fitareq.oldbookstore.data.repository;

import com.fitareq.oldbookstore.data.network.Api;
import com.fitareq.oldbookstore.data.network.ApiService;

public class ProfileSetupRepository {
    private ApiService apiService;
    public ProfileSetupRepository() {
        apiService = Api.getInstance().getApiService();
    }
}
