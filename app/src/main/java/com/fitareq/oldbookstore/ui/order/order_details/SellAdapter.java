package com.fitareq.oldbookstore.ui.order.order_details;

import android.content.Context;
import android.content.Intent;
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
import com.fitareq.oldbookstore.data.model.order.sell_orders.SellOrderResponse;
import com.fitareq.oldbookstore.databinding.ItemBuyOrderBinding;
import com.fitareq.oldbookstore.databinding.ItemSellOrderBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SellAdapter extends RecyclerView.Adapter<SellAdapter.ItemsViewHolder> {

    private List<SellOrderResponse> items;
    private CallBack callBack;

    public SellAdapter(List<SellOrderResponse> items, CallBack callBack) {
        this.items = items;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemsViewHolder(ItemSellOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        SellOrderResponse item = items.get(position);
        Item bookInfo = item.getBookInfo();
        BuyerInfo buyerInfo = item.getBuyerInfo();
        OrderInfo orderInfo = item.getOrderInfo();
        if (bookInfo != null && buyerInfo != null && orderInfo != null) {
            String image = bookInfo.getImage1();
            String title = bookInfo.getTitle();
            String author = bookInfo.getAuthorName();
            String requestedQty = orderInfo.getQty();
            String price = bookInfo.getPrice();
            String totalPayable = String.valueOf(Integer.parseInt(requestedQty) * Double.parseDouble(price));
            String status = orderInfo.getIsAccepted();
            String sellerName = buyerInfo.getName();
            String sellerAddress = buyerInfo.getAddress();

            if (image != null) {
                Picasso.with(context)
                        .load(image)
                        .placeholder(R.drawable.placeholder)
                        .centerCrop()
                        .resize(200, 200)
                        .into(holder.binding.itemImage);
            }

            if (status.equals("0")) {
                holder.binding.phoneLayout.setVisibility(View.GONE);
                holder.binding.acceptBtn.setVisibility(View.VISIBLE);
            } else {

                String sellerPhone = buyerInfo.getPhone();
                holder.binding.buyerPhone.setText(context.getString(R.string.phone, sellerPhone));

                holder.binding.sellerInfoLayout.setVisibility(View.VISIBLE);
                holder.binding.acceptBtn.setVisibility(View.GONE);
            }

            holder.binding.title.setText(title);
            holder.binding.author.setText(context.getString(R.string.author, author));
            holder.binding.requestedQty.setText(context.getString(R.string.requested_quantity, requestedQty));
            holder.binding.price.setText(context.getString(R.string.price, price));
            holder.binding.totalPayable.setText(context.getString(R.string.total_payable, totalPayable));

            holder.binding.buyerName.setText(context.getString(R.string.seller_name, sellerName));
            holder.binding.buyerAddress.setText(context.getString(R.string.seller_address, sellerAddress));

            holder.binding.acceptBtn.setOnClickListener(view -> callBack.acceptOrder(String.valueOf(orderInfo.getId())));
            holder.binding.callBtn.setOnClickListener(view -> {
                callBack.openDialer(buyerInfo.getPhone());
            });
        }else {
            items.remove(position);
            notifyDataSetChanged();
            //holder.binding.mainView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder {
        ItemSellOrderBinding binding;

        public ItemsViewHolder(@NonNull ItemSellOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface CallBack {
        void acceptOrder(String id);

        void openDialer(String phone);
    }
}
