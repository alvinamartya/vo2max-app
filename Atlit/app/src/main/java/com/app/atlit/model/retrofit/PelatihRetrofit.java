package com.app.atlit.model.pojo.retrofit;

import com.app.atlit.model.pojo.PelatihPojo;

public class PelatihRetrofit {
    private boolean status;
    private String message;
    private PelatihPojo data;
    private int id;

    public PelatihRetrofit(boolean status, String message, PelatihPojo data, int id) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.id = id;
    }

    public PelatihRetrofit() {
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

    public PelatihPojo getData() {
        return data;
    }

    public void setData(PelatihPojo data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
