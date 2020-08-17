package com.example.atlit.RetrofitModel;

import com.example.atlit.Model.Cooper;

public class CooperModel {
    private boolean status;
    private String message;
    private Cooper data;

    public CooperModel(boolean status, String message, Cooper data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public CooperModel() {
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

    public Cooper getData() {
        return data;
    }

    public void setData(Cooper data) {
        this.data = data;
    }
}
