package com.fitareq.oldbookstore.data.model.single_category;

import com.fitareq.oldbookstore.data.model.homepage_books.Item;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SingleCategoryData {
    @SerializedName("id")
    @Expose
    private String categoryId;
    @SerializedName("title")
    @Expose
    private String categoryTitle;
    @SerializedName("books")
    @Expose
    private List<Item> categoryBooks;

    public SingleCategoryData(String categoryId, String categoryTitle, List<Item> categoryBooks) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.categoryBooks = categoryBooks;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public List<Item> getCategoryBooks() {
        return categoryBooks;
    }
}
