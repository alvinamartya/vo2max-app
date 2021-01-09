package com.app.atlit.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.atlit.model.pojo.PelariPojo;
import com.app.atlit.model.pojo.UserLoginPojo;
import com.google.gson.Gson;

public class LoginSharedPreference {
    private final String KEY_USERLOGIN = "userlogin";
    private final String KEY_HASLOGIN = "haslogin";
    private final String KEY_ACTIVEAUDIO = "activeaudio";
    private final String KEY_ACTIVESAVE = "activesave";
    private final String KEY_PELARI = "pelari";
    private final String KEY_LAT = "lat";
    private final String KEY_LONG = "long";
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private final Gson gson;

    public LoginSharedPreference(Context context) {
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

    public UserLoginPojo getUserLogin() {
        String jsonToModel = sharedPreferences.getString(KEY_USERLOGIN, null);
        if (jsonToModel != null) return gson.fromJson(jsonToModel, UserLoginPojo.class);
        else return null;
    }

    public void setUserLogin(UserLoginPojo userLoginPojo) {
        String json = userLoginPojo != null ? gson.toJson(userLoginPojo) : null;
        editor.putString(KEY_USERLOGIN, json);
        editor.apply();
    }

    public void setPelari (PelariPojo pelariPojo) {
        String json = pelariPojo != null ? gson.toJson(pelariPojo) : null;
        editor.putString(KEY_PELARI, json);
        editor.apply();
    }

    public PelariPojo getPelari() {
        String json = sharedPreferences.getString(KEY_PELARI,null);
        if (json != null) return gson.fromJson(json, PelariPojo.class);
        else return null;
    }

    public float getLat() {
        return sharedPreferences.getFloat(KEY_LAT, 0);
    }

    public void setLat(float lat) {
        editor.putFloat(KEY_LAT, lat);
        editor.commit();
    }

    public float getLong() {
        return sharedPreferences.getFloat(KEY_LONG, 0);
    }

    public void setLong(float longi) {
        editor.putFloat(KEY_LONG, longi);
        editor.commit();
    }
}
