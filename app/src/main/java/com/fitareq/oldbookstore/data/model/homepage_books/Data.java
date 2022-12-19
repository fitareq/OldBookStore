package com.fitareq.oldbookstore.data.model.homepage_books;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("shop_books")
    @Expose
    private List<Item> shopBooks;
    @SerializedName("other_books")
    @Expose
    private List<Item> otherBooks;

    public Data(List<Item> shopBooks, List<Item> otherBooks) {
        this.shopBooks = shopBooks;
        this.otherBooks = otherBooks;
    }

    public List<Item> getShopBooks() {
        return shopBooks;
    }

    public List<Item> getOtherBooks() {
        return otherBooks;
    }
}
