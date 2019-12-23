package com.example.beer.model;

import com.example.beer.model.dto.Beer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BeerService {

    @GET("beers")
    Call<List<Beer>> getBeers();
}
