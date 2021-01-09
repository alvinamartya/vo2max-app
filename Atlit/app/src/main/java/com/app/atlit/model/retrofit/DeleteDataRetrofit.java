package com.app.atlit.model.pojo.retrofit;

public class DeleteDataRetrofit {
    private boolean status;
    private String message;

    public DeleteDataRetrofit(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public DeleteDataRetrofit() {
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
