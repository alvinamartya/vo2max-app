package com.example.atlit.Utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class GlobalVars {
    public static final String BASE_URl = "http://19ccea46.ngrok.io/api_atlit/index.php/";
//    public static final String BASE_URl = "https://atlit.000webhostapp.com/index.php/";

    public static boolean isValidFormatDate(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
