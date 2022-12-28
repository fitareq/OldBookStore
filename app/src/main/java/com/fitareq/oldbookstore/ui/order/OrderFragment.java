package com.fitareq.oldbookstore.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.fitareq.oldbookstore.R;
import com.fitareq.oldbookstore.databinding.FragmentOrderBinding;
import com.fitareq.oldbookstore.ui.MainActivity;
import com.fitareq.oldbookstore.ui.order.order_details.OrderDetailsActivity;
import com.fitareq.oldbookstore.utils.AppConstants;

public class OrderFragment extends Fragment {

    private FragmentOrderBinding binding;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        binding.buyOrder.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), OrderDetailsActivity.class)
                    .putExtra(AppConstants.KEY_ORDER_TYPE, AppConstants.VALUE_ORDER_TYPE_BUY));
        });
        binding.sellOrder.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), OrderDetailsActivity.class)
                    .putExtra(AppConstants.KEY_ORDER_TYPE, AppConstants.VALUE_ORDER_TYPE_SELL));
        });
        return binding.getRoot();
    }
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) requireActivity()).updateTitle("Orders");
    }
}