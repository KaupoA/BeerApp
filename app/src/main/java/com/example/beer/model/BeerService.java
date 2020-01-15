package com.example.beer.model;

import com.example.beer.model.dto.BeerDto;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BeerService {

    @GET("beers")
    Single<List<BeerDto>> getBeersRx(@Query("page") int page);
}
