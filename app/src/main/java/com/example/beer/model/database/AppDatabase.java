package com.example.beer.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.beer.model.dto.BeerDto;
import com.example.beer.model.dto.ingredients.malt.MaltDto;

@Database(entities = {BeerDto.class, MaltDto.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BeerDao beerDao();
}
