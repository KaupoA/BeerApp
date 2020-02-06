package com.example.beer.homework;

import com.example.beer.model.dto.BeerDto;

public interface BeerListActivityCallback {
    void navigateToBeerDetails(BeerDto beerDto);
    void favouriteButtonClicked(BeerDto beerDto);
    void passShit(String shitName, int howManyShits, int howBigShitis);
}
