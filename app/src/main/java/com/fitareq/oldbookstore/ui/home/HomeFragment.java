package com.fitareq.oldbookstore.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitareq.oldbookstore.R;
import com.fitareq.oldbookstore.data.model.homepage_books.Item;
import com.fitareq.oldbookstore.databinding.FragmentHomeBinding;
import com.fitareq.oldbookstore.ui.MainActivity;
import com.fitareq.oldbookstore.ui.add_book.AddBookActivity;
import com.fitareq.oldbookstore.ui.search.SearchActivity;
import com.fitareq.oldbookstore.utils.CustomDialog;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private CustomDialog dialog;

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
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        dialog = new CustomDialog(requireActivity());


        binding.oldBookStoreItems.more.setOnClickListener(view -> {

        });

        binding.searchFab.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), SearchActivity.class));
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getAllBooks().observe(requireActivity(), allBooks->{
            if (allBooks != null){
                switch (allBooks.getStatus()){
                    case LOADING:
                        dialog.loading();
                        break;
                    case SUCCESS:
                        setupOldBookStoreItems(allBooks.getData().getShopBooks());
                        setupAllItems(allBooks.getData().getOtherBooks());
                        dialog.dismissDialog();
                        break;
                    case FAILED:
                        dialog.error("Error in loading data");
                        break;
                }
            }
        });


        binding.oldBookStoreItems.itemsRv.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.oldBookStoreItems.itemsRv.setAdapter(new ItemsAdapter(new ArrayList<>(), false));
    }

    private void setupAllItems(List<Item> items) {
        binding.allItems.title.setText(R.string.items_for_sale);
        binding.allItems.itemsRv.setLayoutManager(new GridLayoutManager(requireActivity(), 2, RecyclerView.VERTICAL, false));
        binding.allItems.itemsRv.setAdapter(new ItemsAdapter(items, false));
    }

    private void setupOldBookStoreItems(List<Item> items) {
        binding.oldBookStoreItems.title.setText(R.string.app_name);
        binding.oldBookStoreItems.itemsRv.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.oldBookStoreItems.itemsRv.setAdapter(new ItemsAdapter(items, false));
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) requireActivity()).updateTitle("Home");
    }
}