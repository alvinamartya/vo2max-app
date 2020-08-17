package com.example.atlit.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserLogin implements Parcelable {
    private int id;
    private String username;
    private String akses;
    private String cabor;

    public UserLogin() {
    }

    public UserLogin(int id, String username, String akses, String cabor) {
        this.id = id;
        this.username = username;
        this.akses = akses;
        this.cabor = cabor;
    }

    protected UserLogin(Parcel in) {
        id = in.readInt();
        username = in.readString();
        akses = in.readString();
        cabor = in.readString();
    }

    public static final Creator<UserLogin> CREATOR = new Creator<UserLogin>() {
        @Override
        public UserLogin createFromParcel(Parcel in) {
            return new UserLogin(in);
        }

        @Override
        public UserLogin[] newArray(int size) {
            return new UserLogin[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAkses() {
        return akses;
    }

    public void setAkses(String akses) {
        this.akses = akses;
    }

    public String getCabor() {
        return cabor;
    }

    public void setCabor(String cabor) {
        this.cabor = cabor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(username);
        parcel.writeString(akses);
        parcel.writeString(cabor);
    }
}
