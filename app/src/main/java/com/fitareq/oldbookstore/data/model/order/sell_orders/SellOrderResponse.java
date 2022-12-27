package com.fitareq.oldbookstore.data.model.order.sell_orders;

import com.fitareq.oldbookstore.data.model.homepage_books.Item;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SellOrderResponse {
    @SerializedName("order_info")
    @Expose
    private OrderInfo orderInfo;
    @SerializedName("buyer_info")
    @Expose
    private BuyerInfo buyerInfo ;
    @SerializedName("book_info")
    @Expose
    private Item bookInfo;

    public SellOrderResponse(OrderInfo orderInfo, BuyerInfo buyerInfo, Item bookInfo) {
        this.orderInfo = orderInfo;
        this.buyerInfo = buyerInfo;
        this.bookInfo = bookInfo;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public BuyerInfo getBuyerInfo() {
        return buyerInfo;
    }

    public Item getBookInfo() {
        return bookInfo;
    }
}
