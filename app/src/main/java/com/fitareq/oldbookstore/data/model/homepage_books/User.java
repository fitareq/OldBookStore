package com.fitareq.oldbookstore.data.model.homepage_books;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;

    public User(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getTitle() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
