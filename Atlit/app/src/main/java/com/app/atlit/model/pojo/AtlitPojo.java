package com.app.atlit.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class AtlitPojo implements Parcelable {
    private int ID;
    private int refid;
    private String nama;
    private String tanggal_lahir;
    private float tinggi_badan;
    private float berat_badan;
    private String jenis_kelamin;
    private int atlit;
    private String cabang_olahraga;

    public AtlitPojo(int ID, int refid, String nama, String tanggal_lahir, float tinggi_badan, float berat_badan, String jenis_kelamin, int atlit, String cabang_olahraga) {
        this.ID = ID;
        this.refid = refid;
        this.nama = nama;
        this.tanggal_lahir = tanggal_lahir;
        this.tinggi_badan = tinggi_badan;
        this.berat_badan = berat_badan;
        this.jenis_kelamin = jenis_kelamin;
        this.atlit = atlit;
        this.cabang_olahraga = cabang_olahraga;
    }

    public AtlitPojo() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getRefid() {
        return refid;
    }

    public void setRefid(int refid) {
        this.refid = refid;
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

    public float getTinggi_badan() {
        return tinggi_badan;
    }

    public void setTinggi_badan(float tinggi_badan) {
        this.tinggi_badan = tinggi_badan;
    }

    public float getBerat_badan() {
        return berat_badan;
    }

    public void setBerat_badan(float berat_badan) {
        this.berat_badan = berat_badan;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public int getAtlit() {
        return atlit;
    }

    public void setAtlit(int atlit) {
        this.atlit = atlit;
    }

    public String getCabang_olahraga() {
        return cabang_olahraga;
    }

    public void setCabang_olahraga(String cabang_olahraga) {
        this.cabang_olahraga = cabang_olahraga;
    }

    protected AtlitPojo(Parcel in) {
        ID = in.readInt();
        refid = in.readInt();
        nama = in.readString();
        tanggal_lahir = in.readString();
        tinggi_badan = in.readFloat();
        berat_badan = in.readFloat();
        jenis_kelamin = in.readString();
        atlit = in.readInt();
        cabang_olahraga = in.readString();
    }

    public static final Creator<AtlitPojo> CREATOR = new Creator<AtlitPojo>() {
        @Override
        public AtlitPojo createFromParcel(Parcel in) {
            return new AtlitPojo(in);
        }

        @Override
        public AtlitPojo[] newArray(int size) {
            return new AtlitPojo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ID);
        parcel.writeInt(refid);
        parcel.writeString(nama);
        parcel.writeString(tanggal_lahir);
        parcel.writeFloat(tinggi_badan);
        parcel.writeFloat(berat_badan);
        parcel.writeString(jenis_kelamin);
        parcel.writeInt(atlit);
        parcel.writeString(cabang_olahraga);
    }
}
