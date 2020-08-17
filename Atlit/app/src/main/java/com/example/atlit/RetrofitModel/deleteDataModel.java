package com.example.atlit.RetrofitModel;

public class deleteDataModel {
    private boolean status;
    private String message;

    public deleteDataModel(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public deleteDataModel() {
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
}
