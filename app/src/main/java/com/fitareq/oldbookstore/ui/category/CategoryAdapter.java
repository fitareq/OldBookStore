package com.fitareq.oldbookstore.ui.category;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitareq.oldbookstore.data.model.add_book.Category;
import com.fitareq.oldbookstore.databinding.ItemCategoryBinding;
import com.fitareq.oldbookstore.databinding.ItemPhotoBinding;
import com.fitareq.oldbookstore.ui.add_book.AddBookActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.mViewHolder> {

    private List<Category> categories;

    public CategoryAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new mViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        holder.binding.categoryTitle.setText(categories.get(position).getTitle());

        holder.binding.mainLayout.setOnClickListener(view ->
        {

        });
    }

    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }

    class mViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryBinding binding;

        public mViewHolder(@NonNull ItemCategoryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
