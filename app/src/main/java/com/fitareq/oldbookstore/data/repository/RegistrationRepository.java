package com.fitareq.oldbookstore.data.repository;

import com.fitareq.oldbookstore.data.network.ApiService;
import com.fitareq.oldbookstore.data.network.Api;

public class RegistrationRepository {
    private ApiService apiService;

    public RegistrationRepository() {
        apiService = Api.getInstance().getApiService();
    }
}
