package com.fitareq.oldbookstore.data.model;

import com.google.gson.annotations.SerializedName;

public class UpdateTokenBody {
    @SerializedName("device_key")
    private String deviceKey;

    public UpdateTokenBody(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getDeviceKey() {
        return deviceKey;
    }
}
