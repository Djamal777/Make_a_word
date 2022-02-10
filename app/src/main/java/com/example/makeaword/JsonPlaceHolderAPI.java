package com.example.makeaword;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceHolderAPI {
    public static String HOST = "https://engine.lifeis.porn/api/";

    @GET("words.php")
    Call<WordResponse> getWords(
            @Query("rus") Boolean rus,
            @Query("eng") Boolean eng,
            @Query("countries") Boolean countries,
            @Query("cities") Boolean cities,
            @Query("length") String length,
            @Query("apikey") String apikey
    );
}
