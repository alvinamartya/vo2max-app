package com.example.atlit.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cooper implements Parcelable {
    private String nama;
    private int umur;
    private String jenis_kelamin;
    private int waktu;
    private String tingkat_kebugaran;
    private float vo2max;
    private String Solusi;

    public Cooper(String nama, int umur, String jenis_kelamin, int waktu, String tingkat_kebugaran, float vo2max, String solusi) {
        this.nama = nama;
        this.umur = umur;
        this.jenis_kelamin = jenis_kelamin;
        this.waktu = waktu;
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

    public int getWaktu() {
        return waktu;
    }

    public void setWaktu(int waktu) {
        this.waktu = waktu;
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

    protected Cooper(Parcel in) {
        nama = in.readString();
        umur = in.readInt();
        jenis_kelamin = in.readString();
        waktu = in.readInt();
        tingkat_kebugaran = in.readString();
        vo2max = in.readFloat();
        Solusi = in.readString();
    }

    public static final Creator<Cooper> CREATOR = new Creator<Cooper>() {
        @Override
        public Cooper createFromParcel(Parcel in) {
            return new Cooper(in);
        }

        @Override
        public Cooper[] newArray(int size) {
            return new Cooper[size];
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
        parcel.writeInt(waktu);
        parcel.writeString(tingkat_kebugaran);
        parcel.writeFloat(vo2max);
        parcel.writeString(Solusi);
    }
}
