package com.fitareq.oldbookstore.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitareq.oldbookstore.R;
import com.fitareq.oldbookstore.data.model.homepage_books.Item;
import com.fitareq.oldbookstore.databinding.ItemCardBinding;
import com.fitareq.oldbookstore.ui.book_details.BookDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {

    private List<Item> items;
    private boolean isOwnBook;

    public ItemsAdapter(List<Item> items, boolean isOwnBook) {
        this.items = items;
        this.isOwnBook = isOwnBook;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemsViewHolder(ItemCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        //holder.binding..setText(AppConstants.getDate(12345L));
        Item item = items.get(position);
        holder.binding.itemTitle.setText(item.getTitle());
        holder.binding.authorName.setText(holder.itemView.getContext().getString(R.string.author, item.getAuthorName()));
        holder.binding.itemPrice.setText(holder.itemView.getContext().getString(R.string.price, item.getPrice()));

        String quantity = item.getQty();
        if (quantity.equals("0")) {
            holder.binding.itemStatus.setText("Out of stock");
            holder.binding.itemStatus.setTextColor(Color.RED);
        }
        String image = item.getImage1();
        if (image != null) {
            Picasso.with(holder.itemView.getContext()).load(image).fit().into(holder.binding.itemImage);
        }
        holder.binding.getRoot().setOnClickListener(view -> {
            holder.itemView.getContext().startActivity(
                    new Intent(holder.itemView.getContext().getApplicationContext(), BookDetailsActivity.class)
                            .putExtra("id", String.valueOf(item.getId()))
                            .putExtra("is_own_book", isOwnBook));
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder {
        ItemCardBinding binding;

        public ItemsViewHolder(@NonNull ItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
