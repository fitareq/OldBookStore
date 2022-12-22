package com.fitareq.oldbookstore.ui.book_details;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitareq.oldbookstore.databinding.ItemBookDetailsImageBinding;
import com.fitareq.oldbookstore.databinding.ItemCategoryBinding;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.mViewHolder> {

    private List<String> images;

    public ImageAdapter(List<String> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new mViewHolder(ItemBookDetailsImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        Picasso.with(holder.itemView.getContext())
                .load(images.get(position))
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .fit()
                .into(holder.binding.image);
    }

    @Override
    public int getItemCount() {
        return images != null ? images.size() : 0;
    }

    class mViewHolder extends RecyclerView.ViewHolder {
        ItemBookDetailsImageBinding binding;

        public mViewHolder(@NonNull ItemBookDetailsImageBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
