package com.fitareq.oldbookstore.data.model.registration;

import com.google.gson.annotations.SerializedName;

public class RegistrationBody {
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("password")
    private String password;
    @SerializedName("address")
    private String address;
    @SerializedName("lat")
    private String latitude;
    @SerializedName("long")
    private String longitude;

    public RegistrationBody(String name, String email, String phone, String password, String address, String latitude, String longitude) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
