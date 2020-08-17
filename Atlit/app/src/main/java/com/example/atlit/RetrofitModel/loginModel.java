package com.example.atlit.RetrofitModel;

import com.example.atlit.Model.UserLogin;

public class loginModel {
    private boolean status;
    private String message;
    private UserLogin data;

    public loginModel(boolean status, String message, UserLogin data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public loginModel() {
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

    public UserLogin getData() {
        return data;
    }

    public void setData(UserLogin data) {
        this.data = data;
    }
}
