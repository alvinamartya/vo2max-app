package com.app.atlit.utils;

import android.content.Context;

public class MySingleton {
    private static Context context;
    public static void setContext(Context context) {
        MySingleton.context = context;
    }
    public static Context getContext() {
        return MySingleton.context;
    }
}
