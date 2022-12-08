package com.fitareq.oldbookstore.ui.add_book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.fitareq.oldbookstore.databinding.ActivityAddPostBinding;
import com.fitareq.oldbookstore.utils.CustomDialog;

public class AddBookActivity extends AppCompatActivity {

    private ActivityAddPostBinding binding;
    private AddBookViewModel viewModel;
    private CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new CustomDialog(this);
        viewModel = new ViewModelProvider(this).get(AddBookViewModel.class);

        viewModel.addBookResponse.observe(this, response -> {
            switch (response.getStatus()) {
                case LOADING:
                    dialog.loading();
                    break;
                case SUCCESS:
                    dialog.success();
                    break;
                case FAILED:
                    dialog.error("An error occurred!!");
                    break;
            }
        });


    }
}