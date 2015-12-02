package com.cebuinstituteoftechnology_university.citumessenger.Models;

import java.util.Date;

/**
 * Created by Andrew Paul Mago on 12/2/2015.
 */
public class Message {
    private long id;
    private String message;
    private long userId;
    private Date timeStamp;

    public Message() {
    }

    public Message(long id, String message, long userId, Date timeStamp) {
        this.id = id;
        this.message = message;
        this.userId = userId;
        this.timeStamp = timeStamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
