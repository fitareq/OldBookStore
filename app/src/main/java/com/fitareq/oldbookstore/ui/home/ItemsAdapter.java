package com.fitareq.oldbookstore.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitareq.oldbookstore.data.model.item.Item;
import com.fitareq.oldbookstore.databinding.ItemCardBinding;
import com.fitareq.oldbookstore.utils.AppConstants;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {

    private List<Item> items;

    public ItemsAdapter(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemsViewHolder(ItemCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        holder.binding.publishedDate.setText(AppConstants.getDate(12345L));
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder{
        ItemCardBinding binding;
        public ItemsViewHolder(@NonNull ItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
