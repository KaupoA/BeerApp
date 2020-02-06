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
import io.reactivex.Observable;

@Dao
public abstract class BeerDao {

    @Transaction
    public void insert(List<BeerDto> beerDtos){
        for(BeerDto beerDto : beerDtos){
            insertBeer(beerDto);
            for(MaltDto maltDto : beerDto.getIngredients().getMalt()){
                maltDto.setBeerId(beerDto.getId());
                insertMaltDto(maltDto);
            }
        }

        for(BeerDto beerDto : beerDtos) {
            insertBeer(beerDto);
            for(HopsDto hopsDto : beerDto.getIngredients().getHops()) {
                hopsDto.setBeerId(beerDto.getId());
                insertHopsDto(hopsDto);
            }
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void insertBeer(BeerDto beerDto);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void insertMaltDto(MaltDto maltDto);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void insertHopsDto(HopsDto hopsDto);

    public Observable<List<BeerDto>> get(){
        return loadBeerData().map(beerDatas -> transformDataToBeers(beerDatas));
    }
    @Transaction
    @Query("SELECT * FROM beerdto")
    abstract Observable<List<BeerData>> loadBeerData();

    List<BeerDto> transformDataToBeers(List<BeerData> beerDatas){
        List<BeerDto> beerDtos = new ArrayList<>();

        for(BeerData beerData : beerDatas) {
            BeerDto beerDto = beerData.beerDto;
            IngredientsDto ingredientsDto = new IngredientsDto();
            ingredientsDto.setMalt(beerData.maltDtos);
            beerDto.setIngredients(ingredientsDto);
            beerDtos.add(beerDto);
        }

        for(BeerData beerData : beerDatas) {
            BeerDto beerDto = beerData.beerDto;
            IngredientsDto ingredientsDto = new IngredientsDto();
            ingredientsDto.setHops(beerData.hopsDtos);
            beerDto.setIngredients(ingredientsDto);
            beerDtos.add(beerDto);
        }
        return beerDtos;
    }
}
