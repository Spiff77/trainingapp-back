package com.treelevel.app.training.storage;

public enum FolderName {

    TEMP("temp"),
    STUDENTPICS("students/photo");

    private String name;

    FolderName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
