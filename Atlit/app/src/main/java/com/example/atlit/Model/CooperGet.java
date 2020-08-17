package com.example.atlit.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class CooperGet implements Parcelable {
    private int id;
    private String nama;
    private float vo2max;
    private String tingkat_kebugaran;
    private int bulan;
    private int minggu;
    private int waktu;

    public CooperGet(int id, String nama, float vo2max, String tingkat_kebugaran, int bulan, int minggu, int waktu) {
        this.id = id;
        this.nama = nama;
        this.vo2max = vo2max;
        this.tingkat_kebugaran = tingkat_kebugaran;
        this.bulan = bulan;
        this.minggu = minggu;
        this.waktu = waktu;
    }

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

    public int getWaktu() {
        return waktu;
    }

    public void setWaktu(int waktu) {
        this.waktu = waktu;
    }

    protected CooperGet(Parcel in) {
        id = in.readInt();
        nama = in.readString();
        vo2max = in.readFloat();
        tingkat_kebugaran = in.readString();
        bulan = in.readInt();
        minggu = in.readInt();
        waktu = in.readInt();
    }

    public static final Creator<CooperGet> CREATOR = new Creator<CooperGet>() {
        @Override
        public CooperGet createFromParcel(Parcel in) {
            return new CooperGet(in);
        }

        @Override
        public CooperGet[] newArray(int size) {
            return new CooperGet[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nama);
        parcel.writeFloat(vo2max);
        parcel.writeString(tingkat_kebugaran);
        parcel.writeInt(bulan);
        parcel.writeInt(minggu);
        parcel.writeInt(waktu);
    }
}
