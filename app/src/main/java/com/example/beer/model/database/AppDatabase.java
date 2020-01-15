package com.example.beer.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.beer.model.dto.BeerDto;

@Database(entities = {BeerDto.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BeerDao beerDao();
}
