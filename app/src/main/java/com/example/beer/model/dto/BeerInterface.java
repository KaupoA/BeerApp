package com.example.beer.model.dto;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BeerInterface {

    String BASE_URL = "https://api.punkapi.com/v2/";

    @GET("beers")
    Call<String> getString();
}
