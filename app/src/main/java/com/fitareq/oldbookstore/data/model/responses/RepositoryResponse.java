package com.fitareq.oldbookstore.data.model.responses;

import com.fitareq.oldbookstore.data.model.Status;

public class RepositoryResponse<T> {

    private Status status;
    private String message;
    private T data;

    public RepositoryResponse(Status status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> RepositoryResponse<T> success(String message, T data) {
        return new RepositoryResponse<T>(Status.SUCCESS, message, data);
    }

    public static <T> RepositoryResponse<T> error(String message) {
        return new RepositoryResponse<>(Status.FAILED, message, null);
    }

    public static <T> RepositoryResponse<T> loading() {
        return new RepositoryResponse<>(Status.LOADING, null, null);
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
