package com.alon.androiddevtool.models;

import java.io.File;
import java.util.ArrayList;

public class FileRCVState {

    private String path;
    private ArrayList<File> files;

    // Constructors
    public FileRCVState() {
    }

    public FileRCVState(String path, ArrayList<File> files) {
        this.path = path;
        this.files = files;
    }

    // Getters and setters
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }
}
