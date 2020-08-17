package com.example.atlit.RetrofitModel;

import com.example.atlit.Model.Beep;

public class beepModel {
    private boolean status;
    private String message;
    private Beep data;

    public beepModel(boolean status, String message, Beep data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public beepModel() {
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

    public Beep getData() {
        return data;
    }

    public void setData(Beep data) {
        this.data = data;
    }
}
