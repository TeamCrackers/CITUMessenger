package com.cebuinstituteoftechnology_university.citumessenger.Events;

/**
 * Created by osias on 1/23/2016.
 */
public class MessageEvent{
    private String message = null;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
