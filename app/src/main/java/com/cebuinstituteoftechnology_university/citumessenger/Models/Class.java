package com.cebuinstituteoftechnology_university.citumessenger.Models;

import java.util.List;

/**
 * Created by ianosias on 11/11/15.
 */
public class Class {

    private String          id;
    private String          subject;
    private String          section;
    private User            instructor;
    private String          schoolYear;
    private List<User>      students;
    private List<Feed>      feeds;
    private List<String>    resources;

    public Class(String subject, String section, User instructor, String schoolYear) {
        this.subject = subject;
        this.section = section;
        this.instructor = instructor;
        this.schoolYear = schoolYear;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public User getInstructor() {
        return instructor;
    }

    public void setInstructor(User instructor) {
        this.instructor = instructor;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public List<Feed> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<Feed> feeds) {
        this.feeds = feeds;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }
}
