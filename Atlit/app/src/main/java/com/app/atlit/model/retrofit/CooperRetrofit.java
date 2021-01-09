package com.app.atlit.model.pojo.retrofit;

import com.app.atlit.model.pojo.CooperPojo;

public class CooperRetrofit {
    private boolean status;
    private String message;
    private CooperPojo data;

    public CooperRetrofit(boolean status, String message, CooperPojo data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public CooperRetrofit() {
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

    public CooperPojo getData() {
        return data;
    }

    public void setData(CooperPojo data) {
        this.data = data;
    }
}
