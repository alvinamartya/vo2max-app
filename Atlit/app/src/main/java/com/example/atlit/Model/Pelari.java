package com.example.atlit.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pelari implements Parcelable {
    private int id;
    private String name;
    private String jk;
    private int umur;

    public Pelari(int id, String name, String jk, int umur) {
        this.id = id;
        this.name = name;
        this.jk = jk;
        this.umur = umur;
    }

    public Pelari() {
    }

    protected Pelari(Parcel in) {
        id = in.readInt();
        name = in.readString();
        jk = in.readString();
        umur = in.readInt();
    }

    public static final Creator<Pelari> CREATOR = new Creator<Pelari>() {
        @Override
        public Pelari createFromParcel(Parcel in) {
            return new Pelari(in);
        }

        @Override
        public Pelari[] newArray(int size) {
            return new Pelari[size];
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
