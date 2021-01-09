package com.app.atlit.model.pojo.retrofit;

import com.app.atlit.model.pojo.BeepPojo;

public class BeepRetrofit {
    private boolean status;
    private String message;
    private BeepPojo data;

    public BeepRetrofit(boolean status, String message, BeepPojo data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public BeepRetrofit() {
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

    public BeepPojo getData() {
        return data;
    }

    public void setData(BeepPojo data) {
        this.data = data;
    }
}
