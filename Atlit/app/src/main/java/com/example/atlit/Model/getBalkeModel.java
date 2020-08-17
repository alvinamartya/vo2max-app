package com.example.atlit.Model;

import java.util.List;

public class getBalkeModel {
    private boolean status;
    private String message;
    private List<Balke> minggu1;
    private List<Balke> minggu2;
    private List<Balke> minggu3;
    private List<Balke> minggu4;

    public getBalkeModel(boolean status, String message, List<Balke> minggu1, List<Balke> minggu2, List<Balke> minggu3, List<Balke> minggu4) {
        this.status = status;
        this.message = message;
        this.minggu1 = minggu1;
        this.minggu2 = minggu2;
        this.minggu3 = minggu3;
        this.minggu4 = minggu4;
    }

    public getBalkeModel() {
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Balke> getMinggu1() {
        return minggu1;
    }

    public List<Balke> getMinggu2() {
        return minggu2;
    }

    public List<Balke> getMinggu3() {
        return minggu3;
    }

    public List<Balke> getMinggu4() {
        return minggu4;
    }
}
