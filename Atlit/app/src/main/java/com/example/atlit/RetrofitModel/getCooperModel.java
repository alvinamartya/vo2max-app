package com.example.atlit.RetrofitModel;

import com.example.atlit.Model.Cooper;

import java.util.List;

public class getCooperModel {
    private boolean status;
    private String message;
    private List<Cooper> minggu1;
    private List<Cooper> minggu2;
    private List<Cooper> minggu3;
    private List<Cooper> minggu4;

    public getCooperModel(boolean status, String message, List<Cooper> minggu1, List<Cooper> minggu2, List<Cooper> minggu3, List<Cooper> minggu4) {
        this.status = status;
        this.message = message;
        this.minggu1 = minggu1;
        this.minggu2 = minggu2;
        this.minggu3 = minggu3;
        this.minggu4 = minggu4;
    }

    public getCooperModel() {
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

    public List<Cooper> getMinggu1() {
        return minggu1;
    }

    public void setMinggu1(List<Cooper> minggu1) {
        this.minggu1 = minggu1;
    }

    public List<Cooper> getMinggu2() {
        return minggu2;
    }

    public void setMinggu2(List<Cooper> minggu2) {
        this.minggu2 = minggu2;
    }

    public List<Cooper> getMinggu3() {
        return minggu3;
    }

    public void setMinggu3(List<Cooper> minggu3) {
        this.minggu3 = minggu3;
    }

    public List<Cooper> getMinggu4() {
        return minggu4;
    }

    public void setMinggu4(List<Cooper> minggu4) {
        this.minggu4 = minggu4;
    }
}
