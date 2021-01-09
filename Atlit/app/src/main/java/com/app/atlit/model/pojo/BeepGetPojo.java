package com.app.atlit.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class BeepGetPojo implements Parcelable {
    private int id;
    private String nama;
    private float vo2max;
    private String tingkat_kebugaran;
    private int bulan;
    private int minggu;
    private int shutle;
    private int level;

    public BeepGetPojo(int id, String nama, float vo2max, String tingkat_kebugaran, int bulan, int minggu, int shutle, int level) {
        this.id = id;
        this.nama = nama;
        this.vo2max = vo2max;
        this.tingkat_kebugaran = tingkat_kebugaran;
        this.bulan = bulan;
        this.minggu = minggu;
        this.shutle = shutle;
        this.level = level;
    }

    public BeepGetPojo() {
    }

    protected BeepGetPojo(Parcel in) {
        id = in.readInt();
        nama = in.readString();
        vo2max = in.readFloat();
        tingkat_kebugaran = in.readString();
        bulan = in.readInt();
        minggu = in.readInt();
        shutle = in.readInt();
        level = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nama);
        dest.writeFloat(vo2max);
        dest.writeString(tingkat_kebugaran);
        dest.writeInt(bulan);
        dest.writeInt(minggu);
        dest.writeInt(shutle);
        dest.writeInt(level);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BeepGetPojo> CREATOR = new Creator<BeepGetPojo>() {
        @Override
        public BeepGetPojo createFromParcel(Parcel in) {
            return new BeepGetPojo(in);
        }

        @Override
        public BeepGetPojo[] newArray(int size) {
            return new BeepGetPojo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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

    public int getBulan() {
        return bulan;
    }

    public void setBulan(int bulan) {
        this.bulan = bulan;
    }

    public int getMinggu() {
        return minggu;
    }

    public void setMinggu(int minggu) {
        this.minggu = minggu;
    }

    public int getShutle() {
        return shutle;
    }

    public void setShutle(int shutle) {
        this.shutle = shutle;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
