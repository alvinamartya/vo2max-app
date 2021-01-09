package com.app.atlit.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class GlobalVars {
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
