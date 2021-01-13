package com.alon.androiddevtool.converters;

import android.util.Log;

import com.alon.androiddevtool.models.SharedPreferencesField;

import java.util.ArrayList;
import java.util.Map;

public class MapConverter {

    public MapConverter(){

    }

    public ArrayList<SharedPreferencesField> fromMap(Map<String, ?> map) {
        ArrayList<SharedPreferencesField> arrayList = new ArrayList<>();
        SharedPreferencesField field = null;
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            field = new SharedPreferencesField(entry.getKey(), entry.getValue().toString(), entry.getValue().getClass().getSimpleName());
            arrayList.add(field);
        }
        return arrayList;
    }
}
