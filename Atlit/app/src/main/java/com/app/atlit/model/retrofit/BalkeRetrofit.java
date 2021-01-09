package com.app.atlit.model.pojo.retrofit;

import com.app.atlit.model.pojo.BalkePojo;

public class BalkeRetrofit {
    private boolean status;
    private String message;
    private BalkePojo data;

    public BalkeRetrofit(boolean status, String message, BalkePojo data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public BalkeRetrofit() {
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

    public BalkePojo getData() {
        return data;
    }

    public void setData(BalkePojo data) {
        this.data = data;
    }
}
