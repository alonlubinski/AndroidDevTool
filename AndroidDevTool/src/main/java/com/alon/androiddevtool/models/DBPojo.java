package com.alon.androiddevtool.models;

import java.util.ArrayList;

public class DBPojo {

    private String name;
    private int version;
    private ArrayList<String> tables;

    // Constructors
    public DBPojo() {
    }

    public DBPojo(String name, int version, ArrayList<String> tables) {
        this.name = name;
        this.version = version;
        this.tables = tables;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public ArrayList<String> getTables() {
        return tables;
    }

    public void setTables(ArrayList<String> tables) {
        this.tables = tables;
    }
}
