package com.fitareq.oldbookstore.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fitareq.oldbookstore.data.model.order.AcceptOrderResponse;
import com.fitareq.oldbookstore.data.model.order.buy_orders.BuyOrderResponse;
import com.fitareq.oldbookstore.data.model.order.sell_orders.SellOrderResponse;
import com.fitareq.oldbookstore.data.model.profile.UserProfileData;
import com.fitareq.oldbookstore.data.model.responses.ApiResponse;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.network.Api;
import com.fitareq.oldbookstore.data.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {
    private ApiService apiService;
    private MutableLiveData<RepositoryResponse<List<SellOrderResponse>>> _sellOrderResponse;
    private LiveData<RepositoryResponse<List<SellOrderResponse>>> sellOrderResponse;

    private MutableLiveData<RepositoryResponse<List<BuyOrderResponse>>> _buyOrderResponse;
    private LiveData<RepositoryResponse<List<BuyOrderResponse>>> buyOrderResponse;

    private MutableLiveData<RepositoryResponse<AcceptOrderResponse>> _acceptOrderResponse;
    private LiveData<RepositoryResponse<AcceptOrderResponse>> acceptOrderResponse;

    public OrderRepository() {
        apiService = Api.getInstance().getApiService();
        _sellOrderResponse = new MutableLiveData<>();
        sellOrderResponse = _sellOrderResponse;

        _buyOrderResponse = new MutableLiveData<>();
        buyOrderResponse = _buyOrderResponse;

        _acceptOrderResponse = new MutableLiveData<>();
        acceptOrderResponse = _acceptOrderResponse;
    }

    public LiveData<RepositoryResponse<AcceptOrderResponse>> acceptOrder(String url) {
        _acceptOrderResponse.postValue(RepositoryResponse.loading());
        Call<ApiResponse<AcceptOrderResponse>> call = apiService.acceptOrder(url);

        call.enqueue(new Callback<ApiResponse<AcceptOrderResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<AcceptOrderResponse>> call, Response<ApiResponse<AcceptOrderResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _acceptOrderResponse.postValue(RepositoryResponse.success(response.body().getMessage(), response.body().getData()));
                } else _acceptOrderResponse.postValue(RepositoryResponse.error(response.message()));
            }

            @Override
            public void onFailure(Call<ApiResponse<AcceptOrderResponse>> call, Throwable t) {
                _acceptOrderResponse.postValue(RepositoryResponse.error(t.getMessage()));
            }
        });
        return acceptOrderResponse;
    }

    public LiveData<RepositoryResponse<List<BuyOrderResponse>>> getUserBuyOrderInfo() {
        _buyOrderResponse.postValue(RepositoryResponse.loading());

        Call<ApiResponse<List<BuyOrderResponse>>> call = apiService.buyOrderInfo();
        call.enqueue(new Callback<ApiResponse<List<BuyOrderResponse>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<BuyOrderResponse>>> call, Response<ApiResponse<List<BuyOrderResponse>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _buyOrderResponse.postValue(RepositoryResponse.success(response.body().getMessage(), response.body().getData()));
                } else _buyOrderResponse.postValue(RepositoryResponse.error(response.message()));
            }

            @Override
            public void onFailure(Call<ApiResponse<List<BuyOrderResponse>>> call, Throwable t) {
                _buyOrderResponse.postValue(RepositoryResponse.error(t.getMessage()));
            }
        });

        return buyOrderResponse;
    }

    public LiveData<RepositoryResponse<List<SellOrderResponse>>> getUserSellOrderInfo() {
        _sellOrderResponse.postValue(RepositoryResponse.loading());

        Call<ApiResponse<List<SellOrderResponse>>> call = apiService.sellOrderInfo();
        call.enqueue(new Callback<ApiResponse<List<SellOrderResponse>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<SellOrderResponse>>> call, Response<ApiResponse<List<SellOrderResponse>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _sellOrderResponse.postValue(RepositoryResponse.success(response.body().getMessage(), response.body().getData()));
                } else _sellOrderResponse.postValue(RepositoryResponse.error(response.message()));
            }

            @Override
            public void onFailure(Call<ApiResponse<List<SellOrderResponse>>> call, Throwable t) {
                _sellOrderResponse.postValue(RepositoryResponse.error(t.getMessage()));
            }
        });

        return sellOrderResponse;
    }
}
