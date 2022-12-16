package com.fitareq.oldbookstore.data.network;

import com.fitareq.oldbookstore.data.model.add_book.AddBookResponse;
import com.fitareq.oldbookstore.data.model.item.Item;
import com.fitareq.oldbookstore.data.model.login.LoginBody;
import com.fitareq.oldbookstore.data.model.login.LoginResponse;
import com.fitareq.oldbookstore.data.model.registration.RegistrationBody;
import com.fitareq.oldbookstore.data.model.registration.RegistrationResponse;
import com.fitareq.oldbookstore.data.model.responses.ApiResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @POST("login")
    Call<ApiResponse<LoginResponse>> userLogin(@Body LoginBody loginBody);
    @POST("register")
    Call<ApiResponse<RegistrationResponse>> userRegistration(@Body RegistrationBody registrationBody);

    @Multipart
    @POST("books")
    Call<ApiResponse<AddBookResponse>>addPost(
            @Part("title")RequestBody title,
            @Part("author_name")RequestBody author_name,
            @Part("category_id")RequestBody category_id,
            @Part("description")RequestBody description,
            @Part("qty")RequestBody qty,
            @Part("price")RequestBody price,
            @Part  MultipartBody.Part[] image
    );

    @GET("books")
    Call<List<Item>>getAllBooks(@Header("Authorization") String token);
}
