package com.cebuinstituteoftechnology_university.citumessenger.Events;

import com.cebuinstituteoftechnology_university.citumessenger.Models.User;

/**
 * Created by osias on 1/28/2016.
 */
public class AuthenticationEvent {
    public static final short LOGIN_EVENT = 1;
    public static final short LOGIN_ERROR_EVENT = 2;
    public static final String LOGIN_WRONG_CREDENTIALS_ERROR= "School ID or Password is incorrect.";
    public static final String LOGIN_CONNECTION_ERROR= "Cannot connect to server";
    public static final String LOGIN_SUCCESS = "LOGIN SUCCESSFUL";


    private short EventType;
    private User user;
    private String message;

    public short getEventType() {
        return EventType;
    }

    public void setEventType(short eventType) {
        EventType = eventType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
