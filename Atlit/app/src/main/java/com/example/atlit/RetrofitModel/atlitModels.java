package com.example.atlit.RetrofitModel;

import com.example.atlit.Model.Atlit;

import java.util.List;

public class atlitModels {
    private boolean status;
    private String message;
    private List<Atlit> data;

    public atlitModels(boolean status, String message, List<Atlit> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public atlitModels() {
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

    public List<Atlit> getData() {
        return data;
    }

    public void setData(List<Atlit> data) {
        this.data = data;
    }
}
