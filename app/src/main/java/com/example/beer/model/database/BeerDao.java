package com.example.beer.model.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.beer.model.dto.BeerDto;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Dao
public interface BeerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(List<BeerDto> beerDtos);

    @Query("SELECT * FROM beerdto")
    Observable<List<BeerDto>> get();
}
