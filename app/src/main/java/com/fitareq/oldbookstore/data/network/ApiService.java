package com.fitareq.oldbookstore.data.network;

import com.fitareq.oldbookstore.data.model.add_book.AddBookResponse;
import com.fitareq.oldbookstore.data.model.add_book.Category;
import com.fitareq.oldbookstore.data.model.book_details.CreateBookOrderBody;
import com.fitareq.oldbookstore.data.model.book_details.CreateBookOrderResponse;
import com.fitareq.oldbookstore.data.model.homepage_books.HomepageData;
import com.fitareq.oldbookstore.data.model.homepage_books.Item;
import com.fitareq.oldbookstore.data.model.login.LoginBody;
import com.fitareq.oldbookstore.data.model.login.LoginResponse;
import com.fitareq.oldbookstore.data.model.registration.RegistrationResponse;
import com.fitareq.oldbookstore.data.model.responses.ApiResponse;
import com.fitareq.oldbookstore.data.model.single_category.SingleCategoryData;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {
    @POST("login")
    Call<ApiResponse<LoginResponse>> userLogin(@Body LoginBody loginBody);

    @Multipart
    @POST("register")
    Call<ApiResponse<RegistrationResponse>> userRegistration(
            @Part("name")RequestBody name,
            @Part("email")RequestBody email,
            @Part("phone")RequestBody phone,
            @Part("password")RequestBody password,
            @Part("address")RequestBody address,
            @Part("lat")RequestBody lat,
            @Part("lon")RequestBody lon,
            @Part MultipartBody.Part image
    );

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

    @Multipart
    @POST("books")
    Call<ApiResponse<String>>addPostTest(
            @Part("title")RequestBody title,
            @Part("author_name")RequestBody author_name,
            @Part("category_id")RequestBody category_id,
            @Part("description")RequestBody description,
            @Part("qty")RequestBody qty,
            @Part("price")RequestBody price,
            @Part  List<MultipartBody.Part> image
    );

    @GET("homepage-books")
    Call<ApiResponse<HomepageData>>getAllBooks();

    @GET("category")
    Call<ApiResponse<List<Category>>> getAllCategory();

    @GET
    Call<ApiResponse<Item>> getBookDetails(@Url String url);

    @POST("create-order")
    Call<ApiResponse<CreateBookOrderResponse>>createBookOrder(@Body CreateBookOrderBody body);

    @GET
    Call<ApiResponse<SingleCategoryData>> getSingleCategoryBook(@Url String url);

    @GET("search-book/")
    Call<ApiResponse<List<Item>>> getSearchedProduct(@Query("q") String query);
}
