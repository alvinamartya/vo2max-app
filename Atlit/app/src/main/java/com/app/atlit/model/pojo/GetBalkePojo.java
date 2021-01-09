package com.app.atlit.model.pojo;

import java.util.List;

public class GetBalkePojo {
    private boolean status;
    private String message;
    private List<BalkePojo> minggu1;
    private List<BalkePojo> minggu2;
    private List<BalkePojo> minggu3;
    private List<BalkePojo> minggu4;

    public GetBalkePojo(boolean status, String message, List<BalkePojo> minggu1, List<BalkePojo> minggu2, List<BalkePojo> minggu3, List<BalkePojo> minggu4) {
        this.status = status;
        this.message = message;
        this.minggu1 = minggu1;
        this.minggu2 = minggu2;
        this.minggu3 = minggu3;
        this.minggu4 = minggu4;
    }

    public GetBalkePojo() {
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<BalkePojo> getMinggu1() {
        return minggu1;
    }

    public List<BalkePojo> getMinggu2() {
        return minggu2;
    }

    public List<BalkePojo> getMinggu3() {
        return minggu3;
    }

    public List<BalkePojo> getMinggu4() {
        return minggu4;
    }
}
