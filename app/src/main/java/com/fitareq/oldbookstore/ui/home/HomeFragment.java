package com.fitareq.oldbookstore.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitareq.oldbookstore.R;
import com.fitareq.oldbookstore.databinding.FragmentHomeBinding;
import com.fitareq.oldbookstore.ui.add_book.AddBookActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);


        binding.oldBookStoreItems.more.setOnClickListener(view -> {

        });
        binding.addPostFab.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), AddBookActivity.class));
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupOldBookStoreItems();
        setupAllItems();
        binding.oldBookStoreItems.itemsRv.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.oldBookStoreItems.itemsRv.setAdapter(new ItemsAdapter(new ArrayList<>()));
    }

    private void setupAllItems() {
        binding.allItems.title.setText(R.string.items_for_sale);

        binding.allItems.itemsRv.setLayoutManager(new GridLayoutManager(requireActivity(), 2, RecyclerView.VERTICAL, false));
        binding.allItems.itemsRv.setAdapter(new ItemsAdapter(new ArrayList<>()));
    }

    private void setupOldBookStoreItems() {
        binding.oldBookStoreItems.title.setText(R.string.app_name);

        binding.oldBookStoreItems.itemsRv.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.oldBookStoreItems.itemsRv.setAdapter(new ItemsAdapter(new ArrayList<>()));
    }
}