package com.app.atlit.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class UserLoginPojo implements Parcelable {
    private int id;
    private String username;
    private String akses;
    private String cabor;

    public UserLoginPojo() {
    }

    public UserLoginPojo(int id, String username, String akses, String cabor) {
        this.id = id;
        this.username = username;
        this.akses = akses;
        this.cabor = cabor;
    }

    protected UserLoginPojo(Parcel in) {
        id = in.readInt();
        username = in.readString();
        akses = in.readString();
        cabor = in.readString();
    }

    public static final Creator<UserLoginPojo> CREATOR = new Creator<UserLoginPojo>() {
        @Override
        public UserLoginPojo createFromParcel(Parcel in) {
            return new UserLoginPojo(in);
        }

        @Override
        public UserLoginPojo[] newArray(int size) {
            return new UserLoginPojo[size];
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
