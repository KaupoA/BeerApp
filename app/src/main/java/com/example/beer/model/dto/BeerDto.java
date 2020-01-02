package com.example.beer.model.dto;

import java.util.Comparator;

public class BeerDto {

    private String name, image_url;
    private Double abv, ibu, ebc;

    public Double getAbv() {
        return abv;
    }

    public void setAbv(Double abv) {
        this.abv = abv;
    }

    public Double getIbu() {
        return ibu;
    }

    public void setIbu(Double ibu) {
        this.ibu = ibu;
    }

    public Double getEbc() {
        return ebc;
    }

    public void setEbc(Double ebc) {
        this.ebc = ebc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public static class sortByName implements Comparator<BeerDto> {

        @Override
        public int compare(BeerDto o1, BeerDto o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}

