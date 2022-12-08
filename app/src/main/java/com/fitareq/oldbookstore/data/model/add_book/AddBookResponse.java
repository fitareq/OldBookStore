package com.fitareq.oldbookstore.data.model.add_book;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddBookResponse {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("author_name")
    @Expose
    private String authorName;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("image_1")
    @Expose
    private String image1;
    @SerializedName("image_2")
    @Expose
    private String image2;
    @SerializedName("image_3")
    @Expose
    private String image3;
    @SerializedName("image_4")
    @Expose
    private String image4;

    public AddBookResponse(String title, String authorName, String categoryId, String description, String qty, String price, Integer userId, String image1, String image2, String image3, String image4) {
        this.title = title;
        this.authorName = authorName;
        this.categoryId = categoryId;
        this.description = description;
        this.qty = qty;
        this.price = price;
        this.userId = userId;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
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

    public String getDescription() {
        return description;
    }

    public String getQty() {
        return qty;
    }

    public String getPrice() {
        return price;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getImage1() {
        return image1;
    }

    public String getImage2() {
        return image2;
    }

    public String getImage3() {
        return image3;
    }

    public String getImage4() {
        return image4;
    }
}
