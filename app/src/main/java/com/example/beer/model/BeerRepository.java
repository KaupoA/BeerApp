package com.example.beer.model;

import com.example.beer.model.database.BeerDao;
import com.example.beer.model.dto.BeerDto;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class BeerRepository {

    private BeerDao beerDao;
    private BeerService beerService;

    public BeerRepository(BeerDao beerDao, BeerService beerService) {
        this.beerDao = beerDao;
        this.beerService = beerService;
    }

    public Completable insertBeers(List<BeerDto> beerDtos) {
        return Completable.fromAction(() -> {beerDao.insert(beerDtos);});
    }

    public Completable refresh(int pageNumber) {
        return beerService.getBeersRx(pageNumber)
                .flatMapCompletable(this::insertBeers);
    }

    public Observable<List<BeerDto>> get() {
        return beerDao.get();
    }
}
