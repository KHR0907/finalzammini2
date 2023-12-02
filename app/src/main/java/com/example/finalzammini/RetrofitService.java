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