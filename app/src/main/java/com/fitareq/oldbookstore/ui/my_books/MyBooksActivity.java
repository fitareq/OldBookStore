package com.fitareq.oldbookstore.ui.my_books;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitareq.oldbookstore.data.model.homepage_books.Item;
import com.fitareq.oldbookstore.databinding.ActivityMyBooksBinding;
import com.fitareq.oldbookstore.databinding.ActivitySingleCategoryBinding;
import com.fitareq.oldbookstore.ui.home.ItemsAdapter;
import com.fitareq.oldbookstore.ui.single_category.SingleCategoryViewModel;
import com.fitareq.oldbookstore.utils.AppConstants;
import com.fitareq.oldbookstore.utils.CustomDialog;

import java.util.List;

public class MyBooksActivity extends AppCompatActivity {

    private ActivityMyBooksBinding binding;
    private MyBooksViewModel viewModel;
    private CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyBooksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarLay.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Books");
        binding.toolbarLay.toolbar.getNavigationIcon().setTint(Color.parseColor("#ffffff"));


        String userId = getIntent().getStringExtra("id");
        String url = AppConstants.MY_BOOKS_ENDPOINT+userId;
        viewModel = new ViewModelProvider(this).get(MyBooksViewModel.class);
        dialog = new CustomDialog(this);

        viewModel.getMyBooks(url).observe(this, myBooks->{
            if (myBooks !=null){
                switch (myBooks.getStatus()){
                    case LOADING:
                        dialog.loading();
                        break;
                    case SUCCESS:
                        if (myBooks.getData().getBooks()!= null&&!myBooks.getData().getBooks().isEmpty())
                        {
                            setupBooks(myBooks.getData().getBooks());
                        }else {
                            binding.booksRv.setVisibility(View.GONE);
                            binding.nothingFoundLayout.setVisibility(View.VISIBLE);
                        }
                        dialog.dismissDialog();
                        break;
                    case FAILED:
                        dialog.error("Unable to load data!!");
                        break;
                }
            }
        });

        binding.toolbarLay.toolbar.setNavigationOnClickListener(view -> onBackPressed());

    }

    private void setupBooks(List<Item> categoryBooks) {
        binding.booksRv.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
        binding.booksRv.setAdapter(new ItemsAdapter(categoryBooks));
    }
}