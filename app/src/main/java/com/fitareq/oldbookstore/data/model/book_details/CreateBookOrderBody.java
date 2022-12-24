package com.fitareq.oldbookstore.data.model.book_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateBookOrderBody {
    @SerializedName("book_id")
    @Expose
    private String bookId;
    @SerializedName("qty")
    @Expose
    private String quantity;

    public CreateBookOrderBody(String bookId, String quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }

    public String getBookId() {
        return bookId;
    }

    public String getQuantity() {
        return quantity;
    }
}
