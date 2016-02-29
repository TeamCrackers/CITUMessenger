package com.cebuinstituteoftechnology_university.citumessenger.Models;

import java.io.Serializable;

/**
 * Created by osias on 2/29/2016.
 */
public class Request implements Serializable {
    String conversation_id;
    String user_id;
    String from_user_id;

    public Request(String conversation_id, String user_id, String from_user_id) {
        this.conversation_id = conversation_id;
        this.user_id = user_id;
        this.from_user_id = from_user_id;
    }

    public String getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(String conversation_id) {
        this.conversation_id = conversation_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id;
    }
}
