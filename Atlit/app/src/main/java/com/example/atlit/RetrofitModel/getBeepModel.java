package com.example.atlit.RetrofitModel;

import com.example.atlit.Model.Balke;
import com.example.atlit.Model.Beep;

import java.util.List;

public class getBeepModel {
    private boolean status;
    private String message;
    private List<Beep> minggu1;
    private List<Beep> minggu2;
    private List<Beep> minggu3;
    private List<Beep> minggu4;

    public getBeepModel(boolean status, String message, List<Beep> minggu1, List<Beep> minggu2, List<Beep> minggu3, List<Beep> minggu4) {
        this.status = status;
        this.message = message;
        this.minggu1 = minggu1;
        this.minggu2 = minggu2;
        this.minggu3 = minggu3;
        this.minggu4 = minggu4;
    }

    public getBeepModel() {
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

    public List<Beep> getMinggu1() {
        return minggu1;
    }

    public void setMinggu1(List<Beep> minggu1) {
        this.minggu1 = minggu1;
    }

    public List<Beep> getMinggu2() {
        return minggu2;
    }

    public void setMinggu2(List<Beep> minggu2) {
        this.minggu2 = minggu2;
    }

    public List<Beep> getMinggu3() {
        return minggu3;
    }

    public void setMinggu3(List<Beep> minggu3) {
        this.minggu3 = minggu3;
    }

    public List<Beep> getMinggu4() {
        return minggu4;
    }

    public void setMinggu4(List<Beep> minggu4) {
        this.minggu4 = minggu4;
    }
}
