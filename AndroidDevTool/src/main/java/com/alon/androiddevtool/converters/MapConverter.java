package com.alon.androiddevtool.converters;


import com.alon.androiddevtool.models.SharedPreferencesField;

import java.util.ArrayList;
import java.util.Map;

public class MapConverter {

    public MapConverter() {

    }

    /**
     * Function that converts Map to ArrayList.
     *
     * @param map The map to convert.
     * @return ArrayList
     */
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
