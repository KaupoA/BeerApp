package com.example.beer.model.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class BoilVolumeDto implements Parcelable {

    private Integer value;
    private String unit;

    public BoilVolumeDto(Integer value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    private BoilVolumeDto(Parcel in) {
        if (in.readByte() == 0) {
            value = null;
        } else {
            value = in.readInt();
        }
        unit = in.readString();
    }

    public static final Creator<BoilVolumeDto> CREATOR = new Creator<BoilVolumeDto>() {
        @Override
        public BoilVolumeDto createFromParcel(Parcel in) {
            return new BoilVolumeDto(in);
        }

        @Override
        public BoilVolumeDto[] newArray(int size) {
            return new BoilVolumeDto[size];
        }
    };

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (value == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(value);
        }
        dest.writeString(unit);
    }
}
