package com.fitareq.oldbookstore.ui.order.order_details;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitareq.oldbookstore.R;
import com.fitareq.oldbookstore.data.model.homepage_books.Item;
import com.fitareq.oldbookstore.data.model.order.buy_orders.BuyOrderResponse;
import com.fitareq.oldbookstore.data.model.order.sell_orders.BuyerInfo;
import com.fitareq.oldbookstore.data.model.order.sell_orders.OrderInfo;
import com.fitareq.oldbookstore.databinding.ItemBuyOrderBinding;
import com.fitareq.oldbookstore.databinding.ItemCardBinding;
import com.fitareq.oldbookstore.ui.book_details.BookDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BuyAdapter extends RecyclerView.Adapter<BuyAdapter.ItemsViewHolder> {

    private List<BuyOrderResponse> items;

    public BuyAdapter(List<BuyOrderResponse> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemsViewHolder(ItemBuyOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        BuyOrderResponse item = items.get(position);
        Item bookInfo = item.getBookInfo();
        BuyerInfo sellerInfo = item.getSellerInfo();
        OrderInfo orderInfo = item.getOrderInfo();

        String image = bookInfo.getImage1();
        String title = bookInfo.getTitle();
        String author = bookInfo.getAuthorName();
        String requestedQty = orderInfo.getQty();
        String price = bookInfo.getPrice();
        String totalPayable = String.valueOf(Integer.parseInt(requestedQty) * Integer.parseInt(price));
        String status = orderInfo.getIsAccepted();

        if (image != null){
            Picasso.with(context)
                    .load(image)
                    .placeholder(R.drawable.placeholder)
                    .centerCrop()
                    .resize(200,200)
                    .into(holder.binding.itemImage);
        }

        if (status.equals("0")) {
            holder.binding.sellerInfoLayout.setVisibility(View.GONE);
        } else {
            String sellerName = sellerInfo.getName();
            String sellerAddress = sellerInfo.getAddress();
            String sellerPhone = sellerInfo.getPhone();

            holder.binding.sellerName.setText(context.getString(R.string.seller_name, sellerName));
            holder.binding.sellerAddress.setText(context.getString(R.string.seller_address, sellerAddress));
            holder.binding.sellerPhone.setText(context.getString(R.string.phone, sellerPhone));

            holder.binding.sellerInfoLayout.setVisibility(View.VISIBLE);
        }
        holder.binding.title.setText(title);
        holder.binding.author.setText(context.getString(R.string.author, author));
        holder.binding.requestedQty.setText(context.getString(R.string.requested_quantity, requestedQty));
        holder.binding.price.setText(context.getString(R.string.price, price));
        holder.binding.totalPayable.setText(context.getString(R.string.total_payable, totalPayable));
        holder.binding.callBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" +sellerInfo.getPhone()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder {
        ItemBuyOrderBinding binding;

        public ItemsViewHolder(@NonNull ItemBuyOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
