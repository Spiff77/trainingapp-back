package com.treelevel.app.training.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "upload3df1les";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        System.out.println(location);this.location = location;
    }

}
