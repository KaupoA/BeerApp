package com.example.beer.model.dto;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Comparator;

@Entity
public class BeerDto implements Parcelable {

    @PrimaryKey
    private Integer id;
    private String name, image_url, tagline, description;
    private Double abv, ibu, ebc;
    private Boolean favourite;

    @Embedded
    private BoilVolumeDto boil_volume;

    @Ignore
    private IngredientsDto ingredients;

    public BeerDto(Integer id, String name, String image_url, String tagline, String description,
                   Double abv, Double ibu, Double ebc, BoilVolumeDto boil_volume,
                   IngredientsDto ingredients, Boolean favourite) {
        this.id = id;
        this.name = name;
        this.image_url = image_url;
        this.tagline = tagline;
        this.description = description;
        this.abv = abv;
        this.ibu = ibu;
        this.ebc = ebc;
        this.boil_volume = boil_volume;
        this.ingredients = ingredients;
        this.favourite = favourite;
    }

    public BeerDto(Integer id, String name, String image_url, String tagline, String description,
                   Double abv, Double ibu, Double ebc, BoilVolumeDto boil_volume, Boolean favourite) {
        this.id = id;
        this.name = name;
        this.image_url = image_url;
        this.tagline = tagline;
        this.description = description;
        this.abv = abv;
        this.ibu = ibu;
        this.ebc = ebc;
        this.boil_volume = boil_volume;
        this.favourite = favourite;
    }

    protected BeerDto(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        image_url = in.readString();
        tagline = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            abv = null;
        } else {
            abv = in.readDouble();
        }
        if (in.readByte() == 0) {
            ibu = null;
        } else {
            ibu = in.readDouble();
        }
        if (in.readByte() == 0) {
            ebc = null;
        } else {
            ebc = in.readDouble();
        }
        byte tmpFavourite = in.readByte();
        favourite = tmpFavourite == 0 ? null : tmpFavourite == 1;
        boil_volume = in.readParcelable(BoilVolumeDto.class.getClassLoader());
        ingredients = in.readParcelable(IngredientsDto.class.getClassLoader());
    }

    public static final Creator<BeerDto> CREATOR = new Creator<BeerDto>() {
        @Override
        public BeerDto createFromParcel(Parcel in) {
            return new BeerDto(in);
        }

        @Override
        public BeerDto[] newArray(int size) {
            return new BeerDto[size];
        }
    };

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

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(image_url);
        dest.writeString(tagline);
        dest.writeString(description);
        if (abv == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(abv);
        }
        if (ibu == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(ibu);
        }
        if (ebc == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(ebc);
        }
        dest.writeByte((byte) (favourite == null ? 0 : favourite ? 1 : 2));
        dest.writeParcelable(boil_volume, flags);
        dest.writeParcelable(ingredients, flags);
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

