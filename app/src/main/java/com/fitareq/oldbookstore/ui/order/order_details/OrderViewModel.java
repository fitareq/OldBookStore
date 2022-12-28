package com.fitareq.oldbookstore.ui.order.order_details;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fitareq.oldbookstore.data.model.order.AcceptOrderResponse;
import com.fitareq.oldbookstore.data.model.order.buy_orders.BuyOrderResponse;
import com.fitareq.oldbookstore.data.model.order.sell_orders.SellOrderResponse;
import com.fitareq.oldbookstore.data.model.profile.UserProfileData;
import com.fitareq.oldbookstore.data.model.responses.RepositoryResponse;
import com.fitareq.oldbookstore.data.repository.OrderRepository;
import com.fitareq.oldbookstore.data.repository.ProfileRepository;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {
    private OrderRepository repository;
    public OrderViewModel(@NonNull Application application) {
        super(application);
        repository = new OrderRepository(application);
    }

    public LiveData<RepositoryResponse<List<BuyOrderResponse>>> getUserBuyOrderInfo(){
        return repository.getUserBuyOrderInfo();
    }
    public LiveData<RepositoryResponse<List<SellOrderResponse>>> getUserSellOrderInfo(){
        return repository.getUserSellOrderInfo();
    }
    public LiveData<RepositoryResponse<AcceptOrderResponse>> acceptOrder(String url) {
        return repository.acceptOrder(url);
    }
}
