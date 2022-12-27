package com.fitareq.oldbookstore.data.model.order.sell_orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderInfo {
    @SerializedName("id")
    @Expose
    private Integer id;
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
    private String qty;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("is_accepted")
    @Expose
    private String isAccepted;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public OrderInfo(Integer id, String bookId, String buyerId, String sellerId, String qty, String price, String isAccepted, String createdAt, String updatedAt) {
        this.id = id;
        this.bookId = bookId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.qty = qty;
        this.price = price;
        this.isAccepted = isAccepted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
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

    public String getQty() {
        return qty;
    }

    public String getPrice() {
        return price;
    }

    public String getIsAccepted() {
        return isAccepted;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
