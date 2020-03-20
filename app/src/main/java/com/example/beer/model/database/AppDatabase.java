package com.example.beer.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.beer.model.dto.BeerDto;
import com.example.beer.model.dto.HopsDto;
import com.example.beer.model.dto.MaltDto;

@Database(entities = {BeerDto.class, MaltDto.class, HopsDto.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BeerDao beerDao();
}
