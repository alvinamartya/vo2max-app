package com.example.atlit.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pelatih implements Parcelable {
    private int id;
    private String nama;
    private String tanggal_lahir;
    private String cabang_olahraga;

    public Pelatih(int id, String nama, String tanggal_lahir, String cabang_olahraga) {
        this.id = id;
        this.nama = nama;
        this.tanggal_lahir = tanggal_lahir;
        this.cabang_olahraga = cabang_olahraga;
    }

    public Pelatih() {
    }

    protected Pelatih(Parcel in) {
        id = in.readInt();
        nama = in.readString();
        tanggal_lahir = in.readString();
        cabang_olahraga = in.readString();
    }

    public static final Creator<Pelatih> CREATOR = new Creator<Pelatih>() {
        @Override
        public Pelatih createFromParcel(Parcel in) {
            return new Pelatih(in);
        }

        @Override
        public Pelatih[] newArray(int size) {
            return new Pelatih[size];
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
