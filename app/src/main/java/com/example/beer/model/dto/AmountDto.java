package com.example.beer.model.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class AmountDto implements Parcelable {

    private Double value;
    private String unit;

    public AmountDto (Double value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    private AmountDto(Parcel in) {
        if (in.readByte() == 0) {
            value = null;
        } else {
            value = in.readDouble();
        }
        unit = in.readString();
    }

    public static final Creator<AmountDto> CREATOR = new Creator<AmountDto>() {
        @Override
        public AmountDto createFromParcel(Parcel in) {
            return new AmountDto(in);
        }

        @Override
        public AmountDto[] newArray(int size) {
            return new AmountDto[size];
        }
    };

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
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
            dest.writeDouble(value);
        }
        dest.writeString(unit);
    }
}
