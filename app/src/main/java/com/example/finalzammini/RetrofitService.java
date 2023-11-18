package com.example.finalzammini;


import retrofit2.Call;
import retrofit2.http.*;

public interface RetrofitService {

//    /**
//     * @brief "API - GET SINGLE RESOURCE"
//     */
//    @GET("api/users/{id}")
//    Call<ModelUserSingle> doGetUserInfo(
//            @Path("id") int id
//    );
//
//    /**
//     * @brief "API - GET LIST RESOURCE"
//     */
//    @GET("api/users")
//    Call<ModelUserList> doGetUserList(
//            @Query("page") int page
//    );

    /**
     * @brief "API - POST request"
     */
    @FormUrlEncoded
    @POST("chat/completions")
    @Headers({"Content-Type:application/json","Authorization : Bearer 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"})
    Call<ModelUserSingle> doPostUserCreate(
             @Body ModelUserVo modelUserVo
    );
}