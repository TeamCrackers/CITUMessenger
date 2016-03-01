package com.cebuinstituteoftechnology_university.citumessenger.Models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Andrew Paul Mago on 12/2/2015.
 */
public class Message implements Serializable {
    private long id;
    private String message;
    private String userId;
    String conversationId;
    private Date timeStamp;

    public Message() {
    }

    public Message(long id, String message, String userId, Date timeStamp,String conversationId) {
        this.id = id;
        this.message = message;
        this.userId = userId;
        this.timeStamp = timeStamp;
        this.conversationId = conversationId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
