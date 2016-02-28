package com.cebuinstituteoftechnology_university.citumessenger.Models;

import android.net.Uri;

/**
 * Created by Nelson on 2/3/2016.
 */
public class Friends {
    private User userInfo;
    private Uri imgSrc;

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User name) {
        this.userInfo = name;
    }

    public Uri getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(Uri imgSrc) {
        this.imgSrc = imgSrc;
    }
}
