package com.example.beer.model.dto;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class BeerData {

    @Embedded
    public BeerDto beerDto;

    @Relation(parentColumn = "id", entityColumn = "beerId", entity = MaltDto.class)
    public List<MaltDto> maltDtos;

    @Relation(parentColumn = "id", entityColumn = "beerId", entity = HopsDto.class)
    public List<HopsDto> hopsDtos;
}
