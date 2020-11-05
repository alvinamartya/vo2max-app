package com.example.atlit.Model;

import java.util.List;

public class Location {
    private String id;
    private double lat;
    private double lon;
    private double distance;

    public Location(String id, double lat, double lon, double distance) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
