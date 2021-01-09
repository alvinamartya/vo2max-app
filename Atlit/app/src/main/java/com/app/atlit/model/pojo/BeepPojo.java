package com.app.atlit.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class BeepPojo implements Parcelable {
    private String nama;
    private int umur;
    private String jenis_kelamin;
    private int level;
    private int shutle;
    private float vo2max;
    private String tingkat_kebugaran;
    private String solusi;

    public BeepPojo() {
    }

    public BeepPojo(String nama, int umur, String jenis_kelamin, int level, int shutle, float vo2max, String tingkat_kebugaran, String solusi) {
        this.nama = nama;
        this.umur = umur;
        this.jenis_kelamin = jenis_kelamin;
        this.level = level;
        this.shutle = shutle;
        this.vo2max = vo2max;
        this.tingkat_kebugaran = tingkat_kebugaran;
        this.solusi = solusi;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getShutle() {
        return shutle;
    }

    public void setShutle(int shutle) {
        this.shutle = shutle;
    }

    public float getVo2max() {
        return vo2max;
    }

    public void setVo2max(float vo2max) {
        this.vo2max = vo2max;
    }

    public String getTingkat_kebugaran() {
        return tingkat_kebugaran;
    }

    public void setTingkat_kebugaran(String tingkat_kebugaran) {
        this.tingkat_kebugaran = tingkat_kebugaran;
    }

    public String getSolusi() {
        return solusi;
    }

    public void setSolusi(String solusi) {
        this.solusi = solusi;
    }

    protected BeepPojo(Parcel in) {
        nama = in.readString();
        umur = in.readInt();
        jenis_kelamin = in.readString();
        level = in.readInt();
        shutle = in.readInt();
        vo2max = in.readFloat();
        tingkat_kebugaran = in.readString();
        solusi = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama);
        dest.writeInt(umur);
        dest.writeString(jenis_kelamin);
        dest.writeInt(level);
        dest.writeInt(shutle);
        dest.writeFloat(vo2max);
        dest.writeString(tingkat_kebugaran);
        dest.writeString(solusi);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BeepPojo> CREATOR = new Creator<BeepPojo>() {
        @Override
        public BeepPojo createFromParcel(Parcel in) {
            return new BeepPojo(in);
        }

        @Override
        public BeepPojo[] newArray(int size) {
            return new BeepPojo[size];
        }
    };
}
