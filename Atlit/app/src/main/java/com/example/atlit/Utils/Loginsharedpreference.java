package com.example.atlit.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.atlit.Model.Pelari;
import com.example.atlit.Model.UserLogin;
import com.google.gson.Gson;

public class Loginsharedpreference {
    private String KEY_USERLOGIN = "userlogin";
    private String KEY_HASLOGIN = "haslogin";
    private String KEY_ACTIVEAUDIO = "activeaudio";
    private String KEY_ACTIVESAVE = "activesave";
    private String KEY_PELARI = "pelari";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public Loginsharedpreference(Context context) {
        String PREF_NAME = "loginpref";
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
    }

    public boolean getHasLogin() {
        return sharedPreferences.getBoolean(KEY_HASLOGIN, false);
    }

    public void setHasLogin(boolean hashLogin) {
        editor.putBoolean(KEY_HASLOGIN, hashLogin);
        editor.apply();
    }

    public boolean getActiveSave() {
        return sharedPreferences.getBoolean(KEY_ACTIVESAVE, true);
    }

    public void setActiveSave(boolean value) {
        editor.putBoolean(KEY_ACTIVESAVE,value);
        editor.apply();
    }

    public boolean getAcvieAudio() {
        return sharedPreferences.getBoolean(KEY_ACTIVEAUDIO,true);
    }

    public void setActiveAudio(boolean value) {
        editor.putBoolean(KEY_ACTIVEAUDIO,value);
        editor.apply();
    }

    public UserLogin getUserLogin() {
        String jsonToModel = sharedPreferences.getString(KEY_USERLOGIN, null);
        if (jsonToModel != null) return gson.fromJson(jsonToModel, UserLogin.class);
        else return null;
    }

    public void setUserLogin(UserLogin userLogin) {
        String json = userLogin != null ? gson.toJson(userLogin) : null;
        editor.putString(KEY_USERLOGIN, json);
        editor.apply();
    }

    public void setPelari (Pelari pelari) {
        String json = pelari != null ? gson.toJson(pelari) : null;
        editor.putString(KEY_PELARI, json);
        editor.apply();
    }

    public Pelari getPelari() {
        String json = sharedPreferences.getString(KEY_PELARI,null);
        if (json != null) return gson.fromJson(json, Pelari.class);
        else return null;
    }
}
