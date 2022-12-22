package com.fitareq.oldbookstore.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.fitareq.oldbookstore.R;
import com.fitareq.oldbookstore.databinding.FragmentOrderBinding;

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
        return binding.getRoot();
    }
}