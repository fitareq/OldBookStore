package com.fitareq.oldbookstore.data.model.my_books;

import com.fitareq.oldbookstore.data.model.homepage_books.Item;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyBooksResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email_verified_at")
    @Expose
    private String emailVerifiedAt;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("is_system_shop")
    @Expose
    private String isSystemShop;
    @SerializedName("remember_token")
    @Expose
    private String rememberToken;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("books")
    @Expose
    private List<Item> books;

    public MyBooksResponse(Integer id, String name, String email, String phone, String emailVerifiedAt, String image, String address, String lat, String lon, String password, String isSystemShop, String rememberToken, String createdAt, String updatedAt, List<Item> books) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.emailVerifiedAt = emailVerifiedAt;
        this.image = image;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.password = password;
        this.isSystemShop = isSystemShop;
        this.rememberToken = rememberToken;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.books = books;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public String getImage() {
        return image;
    }

    public String getAddress() {
        return address;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getPassword() {
        return password;
    }

    public String getIsSystemShop() {
        return isSystemShop;
    }

    public String getRememberToken() {
        return rememberToken;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public List<Item> getBooks() {
        return books;
    }
}
