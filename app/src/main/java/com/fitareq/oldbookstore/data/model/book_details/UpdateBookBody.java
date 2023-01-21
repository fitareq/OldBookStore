package com.fitareq.oldbookstore.data.model.book_details;

import com.google.gson.annotations.SerializedName;

public class UpdateBookBody {
    @SerializedName("price")
    private String price;

    public UpdateBookBody(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }
}
