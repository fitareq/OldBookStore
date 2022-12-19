package com.fitareq.oldbookstore.data.model.registration;

import java.io.File;

public class RegistrationBody {
    private String name;
    private String email;
    private String phone;
    private String password;
    private String address;
    private String latitude;
    private String longitude;
    private File file;

    public RegistrationBody(String name, String email, String phone, String password, String address, String latitude, String longitude, File file) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.file = file;
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

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public File getFile() {
        return file;
    }
}
