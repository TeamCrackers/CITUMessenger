package com.cebuinstituteoftechnology_university.citumessenger.Models;

/**
 * Created by ianosias on 11/11/15.
 */
public class Feed {

    private User author;
    private String message;

    public Feed(User author, String message) {
        this.author = author;
        this.message = message;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
