package com.app.atlit.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class BalkePojo implements Parcelable {
    private String nama;
    private String tanggal_lahir;
    private String jenis_kelamin;
    private float jarak_ditempuh;
    private String tingkat_kebugaran;
    private float vo2max;
    private String Solusi;

    public BalkePojo() {
    }

    public BalkePojo(String nama, String tanggal_lahir, String jenis_kelamin, float jarak_ditempuh, String tingkat_kebugaran, float vo2max, String solusi) {
        this.nama = nama;
        this.tanggal_lahir = tanggal_lahir;
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

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
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

    protected BalkePojo(Parcel in) {
        nama = in.readString();
        tanggal_lahir = in.readString();
        jenis_kelamin = in.readString();
        jarak_ditempuh = in.readFloat();
        tingkat_kebugaran = in.readString();
        vo2max = in.readFloat();
        Solusi = in.readString();
    }

    public static final Creator<BalkePojo> CREATOR = new Creator<BalkePojo>() {
        @Override
        public BalkePojo createFromParcel(Parcel in) {
            return new BalkePojo(in);
        }

        @Override
        public BalkePojo[] newArray(int size) {
            return new BalkePojo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama);
        dest.writeString(tanggal_lahir);
        dest.writeString(jenis_kelamin);
        dest.writeFloat(jarak_ditempuh);
        dest.writeString(tingkat_kebugaran);
        dest.writeFloat(vo2max);
        dest.writeString(Solusi);
    }
}
