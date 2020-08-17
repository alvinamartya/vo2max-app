package com.example.atlit.RetrofitModel;

import com.example.atlit.Model.Balke;

public class BalkeModel {
    private boolean status;
    private String message;
    private Balke data;

    public BalkeModel(boolean status, String message, Balke data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public BalkeModel() {
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

    public Balke getData() {
        return data;
    }

    public void setData(Balke data) {
        this.data = data;
    }
}
