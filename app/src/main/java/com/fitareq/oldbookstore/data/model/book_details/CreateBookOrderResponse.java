package com.fitareq.oldbookstore.data.model.book_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateBookOrderResponse {
    @SerializedName("book_id")
    @Expose
    private String bookId;

    @SerializedName("buyer_id")
    @Expose
    private String buyerId;

    @SerializedName("seller_id")
    @Expose
    private String sellerId;

    @SerializedName("qty")
    @Expose
    private String quantity;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("id")
    @Expose
    private String orderId;

    public CreateBookOrderResponse(String bookId, String buyerId, String sellerId, String quantity, String price, String orderId) {
        this.bookId = bookId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.quantity = quantity;
        this.price = price;
        this.orderId = orderId;
    }

    public String getBookId() {
        return bookId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public String getOrderId() {
        return orderId;
    }
}
