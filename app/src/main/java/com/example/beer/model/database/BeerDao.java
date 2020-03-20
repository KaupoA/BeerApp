package com.example.beer.model.database;

import com.example.beer.model.dto.BeerData;
import com.example.beer.model.dto.BeerDto;
import com.example.beer.model.dto.HopsDto;
import com.example.beer.model.dto.IngredientsDto;
import com.example.beer.model.dto.MaltDto;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import io.reactivex.Observable;

@Dao

public abstract class BeerDao {

    @Transaction
    public void replaceBeers(List<BeerDto> beerDtos) {
        List<Integer> favouritesIds = getFavouriteIds();
        delete(beerDtos);
        insert(beerDtos, favouritesIds);
    }

    private void insert(List<BeerDto> beerDtos, List<Integer> favouritesIds) {
        for (BeerDto beerDto : beerDtos) {
            Boolean isFav = favouritesIds != null && favouritesIds.contains(beerDto.getId());
            beerDto.setFavourite(isFav);
            insertBeer(beerDto);
            for (MaltDto maltDto : beerDto.getIngredients().getMalt()) {
                maltDto.setBeerId(beerDto.getId());
                insertMaltDto(maltDto);
            }
            for (HopsDto hopsDto : beerDto.getIngredients().getHops()) {
                hopsDto.setBeerId(beerDto.getId());
                insertHopsDto(hopsDto);
            }
        }
    }

    private void delete(List<BeerDto> beerDtos) {
        for (BeerDto beerDto : beerDtos) {
            deleteBeerDto(beerDto.getId());
            deleteMaltDto(beerDto.getId());
            deleteHopsDto(beerDto.getId());
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void insertBeer(BeerDto beerDto);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void insertMaltDto(MaltDto maltDto);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void insertHopsDto(HopsDto hopsDto);

    @Query("DELETE FROM BeerDto WHERE id=:id")
    protected abstract void deleteBeerDto(int id);

    @Query("DELETE FROM MaltDto WHERE beerId=:beerId")
    protected abstract void deleteMaltDto(int beerId);

    @Query("DELETE FROM HopsDto WHERE beerId=:beerId")
    protected abstract void deleteHopsDto(int beerId);

    @Query("SELECT id FROM BeerDto WHERE favourite=1")
    protected abstract List<Integer> getFavouriteIds();

    @Update
    public abstract void updateBeer(BeerDto beerDto);

    public Observable<List<BeerDto>> get() {
        return loadBeerData().map(beerDatas -> transformDataToBeers(beerDatas));
    }

    @Transaction
    @Query("SELECT * FROM beerdto")
    abstract Observable<List<BeerData>> loadBeerData();

    List<BeerDto> transformDataToBeers(List<BeerData> beerDatas) {
        List<BeerDto> beerDtos = new ArrayList<>();

        for (BeerData beerData : beerDatas) {
            BeerDto beerDto = beerData.beerDto;
            IngredientsDto ingredientsDto = new IngredientsDto();
            ingredientsDto.setMalt(beerData.maltDtos);
            ingredientsDto.setHops(beerData.hopsDtos);
            beerDto.setIngredients(ingredientsDto);
            beerDtos.add(beerDto);
        }
        return beerDtos;
    }
}
