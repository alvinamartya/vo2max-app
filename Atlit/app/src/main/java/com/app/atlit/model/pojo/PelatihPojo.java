package com.app.atlit.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class PelatihPojo implements Parcelable {
    private int id;
    private String nama;
    private String tanggal_lahir;
    private String cabang_olahraga;

    public PelatihPojo(int id, String nama, String tanggal_lahir, String cabang_olahraga) {
        this.id = id;
        this.nama = nama;
        this.tanggal_lahir = tanggal_lahir;
        this.cabang_olahraga = cabang_olahraga;
    }

    public PelatihPojo() {
    }

    protected PelatihPojo(Parcel in) {
        id = in.readInt();
        nama = in.readString();
        tanggal_lahir = in.readString();
        cabang_olahraga = in.readString();
    }

    public static final Creator<PelatihPojo> CREATOR = new Creator<PelatihPojo>() {
        @Override
        public PelatihPojo createFromParcel(Parcel in) {
            return new PelatihPojo(in);
        }

        @Override
        public PelatihPojo[] newArray(int size) {
            return new PelatihPojo[size];
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

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getCabang_olahraga() {
        return cabang_olahraga;
    }

    public void setCabang_olahraga(String cabang_olahraga) {
        this.cabang_olahraga = cabang_olahraga;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nama);
        parcel.writeString(tanggal_lahir);
        parcel.writeString(cabang_olahraga);
    }
}
