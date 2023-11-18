package com.example.finalzammini;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    /**
     * @brief "API - GET SINGLE RESOURCE"
     */
    @GET("api/users/{id}")
    Call<ModelUserSingle> doGetUserInfo(
            @Path("id") int id
    );

    /**
     * @brief "API - GET LIST RESOURCE"
     */
    @GET("api/users")
    Call<ModelUserList> doGetUserList(
            @Query("page") int page
    );

    /**
     * @brief "API - POST Login Access"
     */
    @FormUrlEncoded
    @POST("api/users/edit")
    Call<ModelUserCreate> doPostUserCreate(
            @Field("first_name") String first_name
            , @Field("last_name") String last_name
            , @Field("name") String name
    );
}