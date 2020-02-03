package com.example.beer.model.dto;

import com.example.beer.model.dto.ingredients.IngredientsDto;

import java.util.Comparator;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class BeerDto {

    @PrimaryKey
    private Integer id;
    private String name, image_url, tagline, description;
    private Double abv, ibu, ebc;

    @Embedded
    private BoilVolumeDto boil_volume;

    @Ignore
    private IngredientsDto ingredients;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public BoilVolumeDto getBoil_volume() {
        return boil_volume;
    }

    public void setBoil_volume(BoilVolumeDto boil_volume) {
        this.boil_volume = boil_volume;
    }

    public IngredientsDto getIngredients() {
        return ingredients;
    }

    public void setIngredients(IngredientsDto ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class sortByAbvAsc implements Comparator<BeerDto> {
        @Override
        public int compare(BeerDto o1, BeerDto o2) {
            Double abv1 = 0.0;
            Double abv2 = 0.0;
            if (o1.getAbv() != null) {
                abv1 = o1.getAbv();
            }
            if (o2.getAbv() != null) {
                abv2 = o2.getAbv();
            }
            return abv1.compareTo(abv2);
        }
    }

    public static class sortByIbuAsc implements Comparator<BeerDto> {
        @Override
        public int compare(BeerDto o1, BeerDto o2) {
            Double ibu1 = 0.0;
            Double ibu2 = 0.0;
            if (o1.getIbu() != null) {
                ibu1 = o1.getIbu();
            }
            if (o2.getIbu() != null) {
                ibu2 = o2.getIbu();
            }
            return ibu1.compareTo(ibu2);
        }
    }

    public static class sortByEbcAsc implements Comparator<BeerDto> {
        @Override
        public int compare(BeerDto o1, BeerDto o2) {
            Double ebc1 = 0.0;
            Double ebc2 = 0.0;
            if (o1.getEbc() != null) {
                ebc1 = o1.getEbc();
            }
            if (o2.getEbc() != null) {
                ebc2 = o2.getEbc();
            }
            return ebc1.compareTo(ebc2);
        }
    }

    public static class sortByAbvDesc implements Comparator<BeerDto> {
        @Override
        public int compare(BeerDto o1, BeerDto o2) {
            Double abv1 = 0.0;
            Double abv2 = 0.0;
            if (o1.getAbv() != null) {
                abv1 = o1.getAbv();
            }
            if (o2.getAbv() != null) {
                abv2 = o2.getAbv();
            }
            return abv2.compareTo(abv1);
        }
    }

    public static class sortByIbuDesc implements Comparator<BeerDto> {
        @Override
        public int compare(BeerDto o1, BeerDto o2) {
            Double ibu1 = 0.0;
            Double ibu2 = 0.0;
            if (o1.getIbu() != null) {
                ibu1 = o1.getIbu();
            }
            if (o2.getIbu() != null) {
                ibu2 = o2.getIbu();
            }
            return ibu2.compareTo(ibu1);
        }
    }

    public static class sortByEbcDesc implements Comparator<BeerDto> {
        @Override
        public int compare(BeerDto o1, BeerDto o2) {
            Double ebc1 = 0.0;
            Double ebc2 = 0.0;
            if (o1.getEbc() != null) {
                ebc1 = o1.getEbc();
            }
            if (o2.getEbc() != null) {
                ebc2 = o2.getEbc();
            }
            return ebc2.compareTo(ebc1);
        }
    }
}

