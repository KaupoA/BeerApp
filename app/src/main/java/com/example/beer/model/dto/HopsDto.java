package com.example.beer.model.dto;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HopsDto implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private Integer beerId;

    private String name, add, attribute;

    @Embedded
    private AmountDto amount;

    public HopsDto(Integer id, Integer beerId, String name, String add, String attribute, AmountDto amount) {
        this.id = id;
        this.beerId = beerId;
        this.name = name;
        this.add = add;
        this.attribute = attribute;
        this.amount = amount;
    }

    private HopsDto(Parcel in) {
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
        add = in.readString();
        attribute = in.readString();
        amount = in.readParcelable(AmountDto.class.getClassLoader());
    }

    public static final Creator<HopsDto> CREATOR = new Creator<HopsDto>() {
        @Override
        public HopsDto createFromParcel(Parcel in) {
            return new HopsDto(in);
        }

        @Override
        public HopsDto[] newArray(int size) {
            return new HopsDto[size];
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

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
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
        dest.writeString(add);
        dest.writeString(attribute);
        dest.writeParcelable(amount, flags);
    }
}
