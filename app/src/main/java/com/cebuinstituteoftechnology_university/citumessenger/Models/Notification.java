package com.cebuinstituteoftechnology_university.citumessenger.Models;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by osias on 1/28/2016.
 */
public class Notification implements Serializable{
    private String title;
    private String description;
    private Uri imgSrc;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Uri getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(Uri imgSrc) {
        this.imgSrc = imgSrc;
    }
}
