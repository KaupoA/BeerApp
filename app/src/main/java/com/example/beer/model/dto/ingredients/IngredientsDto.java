package com.example.beer.model.dto.ingredients;

import com.example.beer.model.dto.ingredients.malt.MaltDto;

import java.util.List;

public class IngredientsDto {

    private List<MaltDto> malt = null;

    public List<MaltDto> getMalt() {
        return malt;
    }

    public void setMalt(List<MaltDto> malt) {
        this.malt = malt;
    }
}
