package com.fitareq.oldbookstore.ui.add_book;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitareq.oldbookstore.databinding.ItemPhotoBinding;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class AddImageAdapter extends RecyclerView.Adapter<AddImageAdapter.mViewHolder> {

    private ArrayList<File> images;

    public AddImageAdapter(ArrayList<File> images) {
        this.images = images;
    }

    public ArrayList<File> getImages() {
        return images;
    }

    public void setImages(ArrayList<File> images) {
        if (getItemCount() == 0){
            this.images = images;
        }else {
            this.images.addAll(images);
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new mViewHolder(ItemPhotoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        Picasso.with(holder.itemView.getContext()).load(images.get(position)).memoryPolicy(MemoryPolicy.NO_CACHE).fit().into(holder.binding.photo);
    }

    @Override
    public int getItemCount() {
        int size = images != null ? images.size() : 0;
        AddBookActivity.selectedImage = size;
        return size;
    }

    class mViewHolder extends RecyclerView.ViewHolder {
        ItemPhotoBinding binding;

        public mViewHolder(@NonNull ItemPhotoBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
