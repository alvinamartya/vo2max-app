package com.example.atlit.RetrofitModel;

import com.example.atlit.Model.Atlit;

public class atlitModel {
    private boolean status;
    private String message;
    private Atlit data;
    private int id;

    public atlitModel(boolean status, String message, Atlit data, int id) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.id = id;
    }

    public atlitModel() {
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

    public Atlit getData() {
        return data;
    }

    public void setData(Atlit data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
