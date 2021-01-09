package com.app.atlit.model.pojo.retrofit;

import com.app.atlit.model.pojo.AtlitPojo;

import java.util.List;

public class ListAtlitRetrofit {
    private boolean status;
    private String message;
    private List<AtlitPojo> data;

    public ListAtlitRetrofit(boolean status, String message, List<AtlitPojo> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ListAtlitRetrofit() {
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

    public List<AtlitPojo> getData() {
        return data;
    }

    public void setData(List<AtlitPojo> data) {
        this.data = data;
    }
}
