package com.app.atlit.model.pojo.retrofit;

import com.app.atlit.model.pojo.UserLoginPojo;

public class LoginRetrofit {
    private boolean status;
    private String message;
    private UserLoginPojo data;

    public LoginRetrofit(boolean status, String message, UserLoginPojo data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public LoginRetrofit() {
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

    public UserLoginPojo getData() {
        return data;
    }

    public void setData(UserLoginPojo data) {
        this.data = data;
    }
}
