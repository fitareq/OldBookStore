package com.fitareq.oldbookstore.data.model.add_book;

import android.net.Uri;

import java.util.List;

public class AddBookBody {
    private String title;
    private String authorName;
    private String categoryId;
    private List<Uri> image;
    private String description;
    private String quantity;
    private String price;

    public AddBookBody(String title, String authorName, String categoryId, List<Uri> image, String description, String quantity, String price) {
        this.title = title;
        this.authorName = authorName;
        this.categoryId = categoryId;
        this.image = image;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public List<Uri> getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }
}
