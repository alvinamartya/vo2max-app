package com.app.atlit.model.pojo.retrofit;

import com.app.atlit.model.pojo.CooperPojo;

import java.util.List;

public class GetCooperRetrofit {
    private boolean status;
    private String message;
    private List<CooperPojo> minggu1;
    private List<CooperPojo> minggu2;
    private List<CooperPojo> minggu3;
    private List<CooperPojo> minggu4;

    public GetCooperRetrofit(boolean status, String message, List<CooperPojo> minggu1, List<CooperPojo> minggu2, List<CooperPojo> minggu3, List<CooperPojo> minggu4) {
        this.status = status;
        this.message = message;
        this.minggu1 = minggu1;
        this.minggu2 = minggu2;
        this.minggu3 = minggu3;
        this.minggu4 = minggu4;
    }

    public GetCooperRetrofit() {
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

    public List<CooperPojo> getMinggu1() {
        return minggu1;
    }

    public void setMinggu1(List<CooperPojo> minggu1) {
        this.minggu1 = minggu1;
    }

    public List<CooperPojo> getMinggu2() {
        return minggu2;
    }

    public void setMinggu2(List<CooperPojo> minggu2) {
        this.minggu2 = minggu2;
    }

    public List<CooperPojo> getMinggu3() {
        return minggu3;
    }

    public void setMinggu3(List<CooperPojo> minggu3) {
        this.minggu3 = minggu3;
    }

    public List<CooperPojo> getMinggu4() {
        return minggu4;
    }

    public void setMinggu4(List<CooperPojo> minggu4) {
        this.minggu4 = minggu4;
    }
}
