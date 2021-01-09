package com.app.atlit.model.pojo.retrofit;

import com.app.atlit.model.pojo.CabangOlahragaPojo;

import java.util.List;

public class CabangOlahragaRetrofit {
    private boolean status;
    private String message;
    private List<CabangOlahragaPojo> data;

    public CabangOlahragaRetrofit(boolean status, String message, List<CabangOlahragaPojo> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public CabangOlahragaRetrofit() {
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

    public List<CabangOlahragaPojo> getData() {
        return data;
    }

    public void setData(List<CabangOlahragaPojo> data) {
        this.data = data;
    }
}
