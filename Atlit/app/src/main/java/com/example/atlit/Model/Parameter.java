package com.example.atlit.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Parameter implements Parcelable {
    private int stage;
    private double speed;
    private int laps;

    public Parameter() {
    }

    public Parameter(int stage, double speed, int laps) {
        this.stage = stage;
        this.speed = speed;
        this.laps = laps;
    }

    protected Parameter(Parcel in) {
        stage = in.readInt();
        speed = in.readDouble();
        laps = in.readInt();
    }

    public static final Creator<Parameter> CREATOR = new Creator<Parameter>() {
        @Override
        public Parameter createFromParcel(Parcel in) {
            return new Parameter(in);
        }

        @Override
        public Parameter[] newArray(int size) {
            return new Parameter[size];
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
