package com.fitareq.oldbookstore.data.repository;

import com.fitareq.oldbookstore.data.network.ApiService;
import com.fitareq.oldbookstore.data.network.Api;

public class LoginRepository {
    private ApiService apiService;
    public LoginRepository() {
        apiService = Api.getInstance().getApiService();
    }


}
