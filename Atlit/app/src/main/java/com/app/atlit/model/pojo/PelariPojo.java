package com.app.atlit.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class PelariPojo implements Parcelable {
    private int id;
    private String name;
    private String jk;
    private int umur;

    public PelariPojo(int id, String name, String jk, int umur) {
        this.id = id;
        this.name = name;
        this.jk = jk;
        this.umur = umur;
    }

    public PelariPojo() {
    }

    protected PelariPojo(Parcel in) {
        id = in.readInt();
        name = in.readString();
        jk = in.readString();
        umur = in.readInt();
    }

    public static final Creator<PelariPojo> CREATOR = new Creator<PelariPojo>() {
        @Override
        public PelariPojo createFromParcel(Parcel in) {
            return new PelariPojo(in);
        }

        @Override
        public PelariPojo[] newArray(int size) {
            return new PelariPojo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(jk);
        parcel.writeInt(umur);
    }
}
