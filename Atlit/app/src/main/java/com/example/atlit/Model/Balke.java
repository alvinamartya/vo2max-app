package com.example.atlit.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Balke implements Parcelable {
    private String nama;
    private int umur;
    private String jenis_kelamin;
    private float jarak_ditempuh;
    private String tingkat_kebugaran;
    private float vo2max;
    private String Solusi;

    public Balke(String nama, int umur, String jenis_kelamin, float jarak_ditempuh, String tingkat_kebugaran, float vo2max, String solusi) {
        this.nama = nama;
        this.umur = umur;
        this.jenis_kelamin = jenis_kelamin;
        this.jarak_ditempuh = jarak_ditempuh;
        this.tingkat_kebugaran = tingkat_kebugaran;
        this.vo2max = vo2max;
        Solusi = solusi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public float getJarak_ditempuh() {
        return jarak_ditempuh;
    }

    public void setJarak_ditempuh(float jarak_ditempuh) {
        this.jarak_ditempuh = jarak_ditempuh;
    }

    public String getTingkat_kebugaran() {
        return tingkat_kebugaran;
    }

    public void setTingkat_kebugaran(String tingkat_kebugaran) {
        this.tingkat_kebugaran = tingkat_kebugaran;
    }

    public float getVo2max() {
        return vo2max;
    }

    public void setVo2max(float vo2max) {
        this.vo2max = vo2max;
    }

    public String getSolusi() {
        return Solusi;
    }

    public void setSolusi(String solusi) {
        Solusi = solusi;
    }

    protected Balke(Parcel in) {
        nama = in.readString();
        umur = in.readInt();
        jenis_kelamin = in.readString();
        jarak_ditempuh = in.readFloat();
        tingkat_kebugaran = in.readString();
        vo2max = in.readFloat();
        Solusi = in.readString();
    }

    public static final Creator<Balke> CREATOR = new Creator<Balke>() {
        @Override
        public Balke createFromParcel(Parcel in) {
            return new Balke(in);
        }

        @Override
        public Balke[] newArray(int size) {
            return new Balke[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nama);
        parcel.writeInt(umur);
        parcel.writeString(jenis_kelamin);
        parcel.writeFloat(jarak_ditempuh);
        parcel.writeString(tingkat_kebugaran);
        parcel.writeFloat(vo2max);
        parcel.writeString(Solusi);
    }
}
