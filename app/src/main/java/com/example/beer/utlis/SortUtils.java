package com.example.beer.utlis;

import com.example.beer.model.dto.BeerDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortUtils {

    public static List<BeerDto> returnSortedAbvAscList(List<BeerDto> input) {
        List<BeerDto> beerDtos = new ArrayList<>(input);
        Collections.sort(beerDtos, new BeerDto.sortByAbvAsc());
        return beerDtos;
    }

    public static List<BeerDto> returnSortedIbuAscList(List<BeerDto> input) {
        List<BeerDto> beerDtos = new ArrayList<>(input);
        Collections.sort(beerDtos, new BeerDto.sortByIbuAsc());
        return beerDtos;
    }

    public static List<BeerDto> returnSortedEbcAscList(List<BeerDto> input) {
        List<BeerDto> beerDtos = new ArrayList<>(input);
        Collections.sort(beerDtos, new BeerDto.sortByEbcAsc());
        return beerDtos;
    }

    public static List<BeerDto> returnSortedAbvDescList(List<BeerDto> input) {
        List<BeerDto> beerDtos = new ArrayList<>(input);
        Collections.sort(beerDtos, new BeerDto.sortByAbvDesc());
        return beerDtos;
    }

    public static List<BeerDto> returnSortedIbuDescList(List<BeerDto> input) {
        List<BeerDto> beerDtos = new ArrayList<>(input);
        Collections.sort(beerDtos, new BeerDto.sortByIbuDesc());
        return beerDtos;
    }

    public static List<BeerDto> returnSortedEbcDescList(List<BeerDto> input) {
        List<BeerDto> beerDtos = new ArrayList<>(input);
        Collections.sort(beerDtos, new BeerDto.sortByEbcDesc());
        return beerDtos;
    }
}
