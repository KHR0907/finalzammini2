package com.example.finalzammini;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    private static String BASE_URL = "https://reqres.in/";

    public static RetrofitService create() {

        // Retrofit 객체 생성
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(RetrofitService.class);
    }
}