package com.example.atlit.Model;

public class Location2 {
    private double lat;
    private double lon;

    public Location2(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Location2() {
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
