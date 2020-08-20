package com.example.atlit.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public final static String BASE_URL = "http://10.0.2.2/api_atlit/index.php/";
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if(retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
