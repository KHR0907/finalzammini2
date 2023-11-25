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