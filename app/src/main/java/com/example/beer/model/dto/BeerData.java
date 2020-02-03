package com.example.beer.model.dto;

import com.example.beer.model.dto.ingredients.malt.MaltDto;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class BeerData {

    @Embedded
    public BeerDto beerDto;

    @Relation(parentColumn = "id", entityColumn = "beerId", entity = MaltDto.class)
    public List<MaltDto> maltDtos;
}
