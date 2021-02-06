package com.app.atlit.model.pojo;

public class BeepTablePojo {
    private int level;
    private int shuttles;
    private float secondsPerShuttle;
    private boolean isShuttle;

    public BeepTablePojo() {
    }

    public BeepTablePojo(int level, int shuttles, float secondsPerShuttle) {
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
