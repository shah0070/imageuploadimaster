package com.devtides.androidarchitectures.RoomDB.typeconverters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class IntegerArrayConverter {


    @TypeConverter
    public static String intToJSONArray(ArrayList<Integer> arr) {

        if(arr==null){
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        return gson.toJson(arr, type);

    }

    @TypeConverter
    public static ArrayList<Integer> jsonToIntArray(String json){
        if(json==null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
        return gson.fromJson(json, type);
    }

//    @TypeConverter
//    public static long fromDate(Date date) {
//        return date.getTime();
//    }

}
