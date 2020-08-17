package com.example.atlit.RetrofitModel;

import com.example.atlit.Model.CabangOlahraga;

import java.util.List;

public class cabangOlahragaModel {
    private boolean status;
    private String message;
    private List<CabangOlahraga> data;

    public cabangOlahragaModel(boolean status, String message, List<CabangOlahraga> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public cabangOlahragaModel() {
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

    public List<CabangOlahraga> getData() {
        return data;
    }

    public void setData(List<CabangOlahraga> data) {
        this.data = data;
    }
}
