package com.example.finalzammini;


import retrofit2.Call;
import retrofit2.http.*;

public interface RetrofitService {


    /**
     * @brief "API - POST request"
     */
    @POST("chat/completions")
    @Headers({"Content-Type:application/json","Authorization:Bearer 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"})
    Call<JsonResponseDto> sendChat(
             @Body JsonRequestDto jsonRequestDto
    );
}