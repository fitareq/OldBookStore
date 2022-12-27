package com.fitareq.oldbookstore.data.model.order.buy_orders;

import com.fitareq.oldbookstore.data.model.homepage_books.Item;
import com.fitareq.oldbookstore.data.model.order.sell_orders.BuyerInfo;
import com.fitareq.oldbookstore.data.model.order.sell_orders.OrderInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BuyOrderResponse {
    @SerializedName("order_info")
    @Expose
    private OrderInfo orderInfo;
    @SerializedName("seller_info")
    @Expose
    private BuyerInfo sellerInfo ;
    @SerializedName("book_info")
    @Expose
    private Item bookInfo;

    public BuyOrderResponse(OrderInfo orderInfo, BuyerInfo buyerInfo, Item bookInfo) {
        this.orderInfo = orderInfo;
        this.sellerInfo = buyerInfo;
        this.bookInfo = bookInfo;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public BuyerInfo getSellerInfo() {
        return sellerInfo;
    }

    public Item getBookInfo() {
        return bookInfo;
    }
}
