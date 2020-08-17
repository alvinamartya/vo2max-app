package com.example.atlit.RetrofitModel;

import com.example.atlit.Model.Atlit;
import com.example.atlit.Model.Pelatih;

public class pelatihModel {
    private boolean status;
    private String message;
    private Pelatih data;
    private int id;

    public pelatihModel(boolean status, String message, Pelatih data, int id) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.id = id;
    }

    public pelatihModel() {
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

    public Pelatih getData() {
        return data;
    }

    public void setData(Pelatih data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
