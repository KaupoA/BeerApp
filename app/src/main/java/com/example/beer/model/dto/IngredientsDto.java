package com.example.beer.model.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class IngredientsDto implements Parcelable {

    private List<MaltDto> malt;

    private List<HopsDto> hops;

    public IngredientsDto() {

    }

    protected IngredientsDto(Parcel in) {
        malt = in.createTypedArrayList(MaltDto.CREATOR);
        hops = in.createTypedArrayList(HopsDto.CREATOR);
    }

    public static final Creator<IngredientsDto> CREATOR = new Creator<IngredientsDto>() {
        @Override
        public IngredientsDto createFromParcel(Parcel in) {
            return new IngredientsDto(in);
        }

        @Override
        public IngredientsDto[] newArray(int size) {
            return new IngredientsDto[size];
        }
    };

    public List<MaltDto> getMalt() {
        return malt;
    }

    public void setMalt(List<MaltDto> malt) {
        this.malt = malt;
    }

    public List<HopsDto> getHops() {
        return hops;
    }

    public void setHops(List<HopsDto> hops) {
        this.hops = hops;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(malt);
        dest.writeTypedList(hops);
    }
}
