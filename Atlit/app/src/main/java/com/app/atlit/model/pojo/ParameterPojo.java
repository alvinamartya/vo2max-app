package com.app.atlit.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class ParameterPojo implements Parcelable {
    private int stage;
    private double speed;
    private int laps;

    public ParameterPojo() {
    }

    public ParameterPojo(int stage, double speed, int laps) {
        this.stage = stage;
        this.speed = speed;
        this.laps = laps;
    }

    protected ParameterPojo(Parcel in) {
        stage = in.readInt();
        speed = in.readDouble();
        laps = in.readInt();
    }

    public static final Creator<ParameterPojo> CREATOR = new Creator<ParameterPojo>() {
        @Override
        public ParameterPojo createFromParcel(Parcel in) {
            return new ParameterPojo(in);
        }

        @Override
        public ParameterPojo[] newArray(int size) {
            return new ParameterPojo[size];
        }
    };

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getLaps() {
        return laps;
    }

    public void setLaps(int laps) {
        this.laps = laps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stage);
        dest.writeDouble(speed);
        dest.writeInt(laps);
    }
}
