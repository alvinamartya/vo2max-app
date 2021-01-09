package com.app.atlit.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class BalkeGetPojo implements Parcelable {
    private int id;
    private String nama;
    private float vo2max;
    private String tingkat_kebugaran;
    private int bulan;
    private int minggu;
    private float jarak_ditempuh;


    public BalkeGetPojo(int id, String nama, float vo2max, String tingkat_kebugaran, int bulan, int minggu, float jarak_ditempuh) {
        this.id = id;
        this.nama = nama;
        this.vo2max = vo2max;
        this.tingkat_kebugaran = tingkat_kebugaran;
        this.bulan = bulan;
        this.minggu = minggu;
        this.jarak_ditempuh = jarak_ditempuh;
    }

    protected BalkeGetPojo(Parcel in) {
        id = in.readInt();
        nama = in.readString();
        vo2max = in.readFloat();
        tingkat_kebugaran = in.readString();
        bulan = in.readInt();
        minggu = in.readInt();
        jarak_ditempuh = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nama);
        dest.writeFloat(vo2max);
        dest.writeString(tingkat_kebugaran);
        dest.writeInt(bulan);
        dest.writeInt(minggu);
        dest.writeFloat(jarak_ditempuh);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BalkeGetPojo> CREATOR = new Creator<BalkeGetPojo>() {
        @Override
        public BalkeGetPojo createFromParcel(Parcel in) {
            return new BalkeGetPojo(in);
        }

        @Override
        public BalkeGetPojo[] newArray(int size) {
            return new BalkeGetPojo[size];
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

    public float getJarak_ditempuh() {
        return jarak_ditempuh;
    }

    public void setJarak_ditempuh(float jarak_ditempuh) {
        this.jarak_ditempuh = jarak_ditempuh;
    }
}
