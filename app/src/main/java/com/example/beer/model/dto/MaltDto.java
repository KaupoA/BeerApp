package com.example.beer.model.dto;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MaltDto implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private Integer beerId;

    private String name;

    @Embedded
    private AmountDto amount;

    public MaltDto(Integer id, Integer beerId, String name, AmountDto amount) {
        this.id = id;
        this.beerId = beerId;
        this.name = name;
        this.amount = amount;
    }

    private MaltDto(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            beerId = null;
        } else {
            beerId = in.readInt();
        }
        name = in.readString();
        amount = in.readParcelable(AmountDto.class.getClassLoader());
    }

    public static final Creator<MaltDto> CREATOR = new Creator<MaltDto>() {
        @Override
        public MaltDto createFromParcel(Parcel in) {
            return new MaltDto(in);
        }

        @Override
        public MaltDto[] newArray(int size) {
            return new MaltDto[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBeerId() {
        return beerId;
    }

    public void setBeerId(Integer beerId) {
        this.beerId = beerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AmountDto getAmount() {
        return amount;
    }

    public void setAmount(AmountDto amount) {
        this.amount = amount;
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
        if (beerId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(beerId);
        }
        dest.writeString(name);
        dest.writeParcelable(amount, flags);
    }
}
