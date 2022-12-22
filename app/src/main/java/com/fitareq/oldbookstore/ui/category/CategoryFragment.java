package com.fitareq.oldbookstore.ui.category;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.fitareq.oldbookstore.R;
import com.fitareq.oldbookstore.databinding.FragmentCategoryBinding;
import com.fitareq.oldbookstore.ui.MainActivity;
import com.fitareq.oldbookstore.utils.CustomDialog;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;
    private CategoryViewModel viewModel;
    private CustomDialog dialog;

    public CategoryFragment() {
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
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        dialog = new CustomDialog(requireActivity());

        viewModel.getCategories().observe(requireActivity(), categories->{
            if (categories != null){
                switch (categories.getStatus()){
                    case LOADING:
                        dialog.loading();
                        break;
                    case SUCCESS:
                        binding.categoryRv.setLayoutManager(new LinearLayoutManager(requireActivity()));
                        binding.categoryRv.setAdapter(new CategoryAdapter(categories.getData()));
                        dialog.dismissDialog();
                        break;
                    case FAILED:
                        dialog.error("Something went wrong!!");
                        break;
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) requireActivity()).updateTitle("Categories");
    }
}