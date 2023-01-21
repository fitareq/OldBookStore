package com.fitareq.oldbookstore.data.network;

import com.fitareq.oldbookstore.data.model.UpdateTokenBody;
import com.fitareq.oldbookstore.data.model.add_book.AddBookResponse;
import com.fitareq.oldbookstore.data.model.add_book.Category;
import com.fitareq.oldbookstore.data.model.book_details.CreateBookOrderBody;
import com.fitareq.oldbookstore.data.model.book_details.CreateBookOrderResponse;
import com.fitareq.oldbookstore.data.model.book_details.UpdateBookBody;
import com.fitareq.oldbookstore.data.model.homepage_books.HomepageData;
import com.fitareq.oldbookstore.data.model.homepage_books.Item;
import com.fitareq.oldbookstore.data.model.login.LoginBody;
import com.fitareq.oldbookstore.data.model.login.LoginResponse;
import com.fitareq.oldbookstore.data.model.my_books.MyBooksResponse;
import com.fitareq.oldbookstore.data.model.order.AcceptOrderResponse;
import com.fitareq.oldbookstore.data.model.order.buy_orders.BuyOrderResponse;
import com.fitareq.oldbookstore.data.model.order.sell_orders.SellOrderResponse;
import com.fitareq.oldbookstore.data.model.profile.UserProfileData;
import com.fitareq.oldbookstore.data.model.registration.RegistrationResponse;
import com.fitareq.oldbookstore.data.model.responses.ApiResponse;
import com.fitareq.oldbookstore.data.model.single_category.SingleCategoryData;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {
    @POST("login")
    Call<ApiResponse<LoginResponse>> userLogin(@Body LoginBody loginBody);

    @Multipart
    @POST("register")
    Call<ApiResponse<RegistrationResponse>> userRegistration(
            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone,
            @Part("password") RequestBody password,
            @Part("address") RequestBody address,
            @Part("lat") RequestBody lat,
            @Part("lon") RequestBody lon,
            @Part MultipartBody.Part image
    );

    @Multipart
    @POST("books")
    Call<ApiResponse<AddBookResponse>> addPost(
            @Part("title") RequestBody title,
            @Part("author_name") RequestBody author_name,
            @Part("category_id") RequestBody category_id,
            @Part("description") RequestBody description,
            @Part("qty") RequestBody qty,
            @Part("price") RequestBody price,
            @Part MultipartBody.Part[] image
    );

    @GET("homepage-books")
    Call<ApiResponse<HomepageData>> getAllBooks();

    @GET("category")
    Call<ApiResponse<List<Category>>> getAllCategory();

    @GET
    Call<ApiResponse<Item>> getBookDetails(@Url String url);

    @DELETE
    Call<ApiResponse<Object>> deleteBook(@Url String url);

    @PUT
    Call<ApiResponse<Object>> updateBook(@Url String url, @Body UpdateBookBody body);

    @POST("create-order")
    Call<ApiResponse<CreateBookOrderResponse>> createBookOrder(@Body CreateBookOrderBody body);

    @GET
    Call<ApiResponse<SingleCategoryData>> getSingleCategoryBook(@Url String url);

    @GET("search-book/")
    Call<ApiResponse<List<Item>>> getSearchedProduct(@Query("q") String query);

    @GET("me")
    Call<ApiResponse<UserProfileData>> getUserProfile();

    @GET
    Call<ApiResponse<AcceptOrderResponse>> acceptOrder(@Url String url);

    @GET("user-all-buy-orders")
    Call<ApiResponse<List<BuyOrderResponse>>> buyOrderInfo();

    @GET("user-all-sell-orders")
    Call<ApiResponse<List<SellOrderResponse>>> sellOrderInfo();

    @GET
    Call<ApiResponse<MyBooksResponse>> getMyBooks(@Url String url);

    @POST("update-device-key")
    Call<ApiResponse<Object>> updateToken(@Body UpdateTokenBody body);
}
