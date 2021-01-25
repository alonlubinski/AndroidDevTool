package com.alon.androiddevtool.models;

public class SharedPreferencesField {

    private String key;
    private String value;
    private String type;

    // Constructors.
    public SharedPreferencesField(String key, String value, String type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }

    // Getters and setters.
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
