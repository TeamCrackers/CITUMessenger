package com.cebuinstituteoftechnology_university.citumessenger.Models;

import android.net.Uri;

/**
 * Created by Nelson on 2/3/2016.
 */
public class Friends {
    private String name;
    private Uri imgSrc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(Uri imgSrc) {
        this.imgSrc = imgSrc;
    }
}
