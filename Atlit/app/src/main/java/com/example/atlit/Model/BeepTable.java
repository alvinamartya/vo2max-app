package com.example.atlit.Model;

public class BeepTable {
    private int level;
    private int shuttles;
    private float secondsPerShuttle;

    public BeepTable() {
    }

    public BeepTable(int level, int shuttles, float secondsPerShuttle) {
        this.level = level;
        this.shuttles = shuttles;
        this.secondsPerShuttle = secondsPerShuttle;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getShuttles() {
        return shuttles;
    }

    public void setShuttles(int shuttles) {
        this.shuttles = shuttles;
    }

    public float getSecondsPerShuttle() {
        return secondsPerShuttle;
    }

    public void setSecondsPerShuttle(float secondsPerShuttle) {
        this.secondsPerShuttle = secondsPerShuttle;
    }
}
