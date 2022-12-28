package com.fitareq.oldbookstore.ui.order.order_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.fitareq.oldbookstore.databinding.ActivityOrderDetailsBinding;
import com.fitareq.oldbookstore.ui.MainActivity;
import com.fitareq.oldbookstore.utils.AppConstants;
import com.fitareq.oldbookstore.utils.CustomDialog;

public class OrderDetailsActivity extends AppCompatActivity implements SellAdapter.CallBack, BuyAdapter.CallBack{

    private ActivityOrderDetailsBinding binding;
    private OrderViewModel viewModel;
    private CustomDialog dialog;
    String orderType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarLay.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Orders");
        binding.toolbarLay.toolbar.getNavigationIcon().setTint(Color.parseColor("#ffffff"));

        orderType = getIntent().getStringExtra(AppConstants.KEY_ORDER_TYPE);
        viewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        dialog = new CustomDialog(this);

        switch (orderType){
            case AppConstants.VALUE_ORDER_TYPE_BUY:
                getSupportActionBar().setTitle("Buy orders");
                setupBuyOrder();
                break;
            case AppConstants.VALUE_ORDER_TYPE_SELL:
                getSupportActionBar().setTitle("Sell orders");
                setupSellOrder();
                break;
        }

        binding.toolbarLay.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void setupSellOrder() {
        viewModel.getUserSellOrderInfo().observe(this, sellOrderInfo->{
            switch (sellOrderInfo.getStatus()){
                case LOADING:
                    dialog.loading();
                    break;
                case SUCCESS:
                    if (sellOrderInfo.getData() != null && !sellOrderInfo.getData().isEmpty()){
                        binding.ordersRv.setLayoutManager(new LinearLayoutManager(this));
                        binding.ordersRv.setAdapter(new SellAdapter(sellOrderInfo.getData(), this));

                    }else {
                        showNothingFound();
                    }
                    dialog.dismissDialog();
                    break;
                case FAILED:
                    dialog.error("Failed to load data");
                    showNothingFound();
                    break;
            }
        });
    }

    private void setupBuyOrder() {
        viewModel.getUserBuyOrderInfo().observe(this, buyOrderInfo->{
            switch (buyOrderInfo.getStatus()){
                case LOADING:
                    dialog.loading();
                    break;
                case SUCCESS:
                    if (buyOrderInfo.getData() != null && !buyOrderInfo.getData().isEmpty()){
                        binding.ordersRv.setLayoutManager(new LinearLayoutManager(this));
                        binding.ordersRv.setAdapter(new BuyAdapter(buyOrderInfo.getData(), this));

                    }else {
                        showNothingFound();
                    }
                    dialog.dismissDialog();
                    break;
                case FAILED:
                    dialog.error("Failed to load data");
                    showNothingFound();
                    break;
            }
        });
    }

    @Override
    public void acceptOrder(String id) {
        String url = AppConstants.ACCEPT_ORDER_ENDPOINT+id;
        viewModel.acceptOrder(url).observe(this, acceptOrderResponse->{
            switch (acceptOrderResponse.getStatus()){
                case LOADING:
                    dialog.loading();
                    break;
                case SUCCESS:
                    dialog.success();
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(OrderDetailsActivity.this, OrderDetailsActivity.class)
                                    .putExtra(AppConstants.KEY_ORDER_TYPE, orderType));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismissDialog();
                                }
                            });
                        }
                    },1000);
                    break;
                case FAILED:
                    dialog.error("Couldn't accept order. Please try again.");
                    break;
            }
        });
    }

    private void showNothingFound(){
        binding.ordersRv.setVisibility(View.GONE);
        binding.nothingFoundLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void openDialer(String phone) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.setData(Uri.parse("tel:" + phone));
        startActivity(callIntent);
    }

}