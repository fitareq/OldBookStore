package com.fitareq.oldbookstore.data.model;

import com.google.gson.annotations.Expose;

public class ApiResponse<T>{
    @Expose
    private int statusCode;
    @Expose
    private String message;
    @Expose
    private T data;
}
