package com.cebuinstituteoftechnology_university.citumessenger.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by osias on 1/23/2016.
 */
public class Conversation implements Serializable{
    String id;
    List<User> participants;
    List<Message> messages;
    String recentUpdate;



    public Conversation() {
        id = UUID.randomUUID().toString();
        participants = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public void addParticipant(User user){participants.add(user);}
    public void addMessage(Message message){messages.add(message);}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getRecentUpdate() {
        return recentUpdate;
    }

    public void setRecentUpdate(String recentUpdate) {
        this.recentUpdate = recentUpdate;
    }
}
