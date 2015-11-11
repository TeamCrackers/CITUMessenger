package com.cebuinstituteoftechnology_university.citumessenger.Models;

import java.util.List;

/**
 * Created by ianosias on 11/11/15.
 */
public class User {
    public static final int USERTYPE_STUDENT    = 0;
    public static final int USERTYPE_TEACHER    = 1;
    public static final int USERTYPE_ADMIN      = 2;
    // Login Information
    private String id;
    private String password;

    // User Information
    private String firstName;
    private String lastName;
    private String course;
    private int yearLevel;
    private int userType;
    // Messenger information

    private List<User> friends;
    private List<Class> classes;

    // Security Information
    private String accessToken;

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(int yearLevel) {
        this.yearLevel = yearLevel;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
