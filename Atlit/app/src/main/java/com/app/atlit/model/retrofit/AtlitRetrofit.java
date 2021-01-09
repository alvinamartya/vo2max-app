package com.app.atlit.model.pojo.retrofit;

import com.app.atlit.model.pojo.AtlitPojo;

public class AtlitRetrofit {
    private boolean status;
    private String message;
    private AtlitPojo data;
    private int id;

    public AtlitRetrofit(boolean status, String message, AtlitPojo data, int id) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.id = id;
    }

    public AtlitRetrofit() {
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AtlitPojo getData() {
        return data;
    }

    public void setData(AtlitPojo data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
