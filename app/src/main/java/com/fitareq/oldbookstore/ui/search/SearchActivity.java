package com.fitareq.oldbookstore.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitareq.oldbookstore.databinding.ActivitySearchBinding;
import com.fitareq.oldbookstore.ui.home.ItemsAdapter;
import com.fitareq.oldbookstore.utils.CustomDialog;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private SearchViewModel viewModel;
    private CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        dialog = new CustomDialog(this);
        binding.searchBox.requestFocus();

        binding.searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String query = binding.searchBox.getText().toString();
                    getSearchedData(query);
                    if (binding.searchBox.isFocused()) {
                        hideSoftKeyboard();
                        binding.searchBox.clearFocus();
                    }
                    return true;
                }
                return false;
            }
        });

        binding.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });

    }

    private void getSearchedData(String query) {
        viewModel.getSearchedProduct(query).observe(this, searchedData -> {
            if (searchedData != null) {
                switch (searchedData.getStatus()) {
                    case LOADING:
                        dialog.loading();
                        break;
                    case SUCCESS:
                        if (searchedData.getData() != null && !searchedData.getData().isEmpty()) {
                            binding.searchProduct.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
                            binding.searchProduct.setAdapter(new ItemsAdapter(searchedData.getData(), false));
                            showNothingFound(View.VISIBLE, View.GONE);
                        }else {
                            showNothingFound(View.GONE, View.VISIBLE);
                        }
                        dialog.dismissDialog();
                        break;
                    case FAILED:
                        dialog.error("Error in loading data!!");
                        showNothingFound(View.GONE, View.VISIBLE);
                        break;
                }
            }
        });
    }

    private void hideSoftKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showNothingFound(int recyclerViewVisibility, int nothingFoundViewVisibility) {
        binding.searchProduct.setVisibility(recyclerViewVisibility);
        binding.nothingFoundLayout.setVisibility(nothingFoundViewVisibility);
    }
}