package com.app.atlit.model.pojo.retrofit;

import com.app.atlit.model.pojo.BeepPojo;

import java.util.List;

public class GetBeepRetrofit {
    private boolean status;
    private String message;
    private List<BeepPojo> minggu1;
    private List<BeepPojo> minggu2;
    private List<BeepPojo> minggu3;
    private List<BeepPojo> minggu4;

    public GetBeepRetrofit(boolean status, String message, List<BeepPojo> minggu1, List<BeepPojo> minggu2, List<BeepPojo> minggu3, List<BeepPojo> minggu4) {
        this.status = status;
        this.message = message;
        this.minggu1 = minggu1;
        this.minggu2 = minggu2;
        this.minggu3 = minggu3;
        this.minggu4 = minggu4;
    }

    public GetBeepRetrofit() {
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

    public List<BeepPojo> getMinggu1() {
        return minggu1;
    }

    public void setMinggu1(List<BeepPojo> minggu1) {
        this.minggu1 = minggu1;
    }

    public List<BeepPojo> getMinggu2() {
        return minggu2;
    }

    public void setMinggu2(List<BeepPojo> minggu2) {
        this.minggu2 = minggu2;
    }

    public List<BeepPojo> getMinggu3() {
        return minggu3;
    }

    public void setMinggu3(List<BeepPojo> minggu3) {
        this.minggu3 = minggu3;
    }

    public List<BeepPojo> getMinggu4() {
        return minggu4;
    }

    public void setMinggu4(List<BeepPojo> minggu4) {
        this.minggu4 = minggu4;
    }
}
