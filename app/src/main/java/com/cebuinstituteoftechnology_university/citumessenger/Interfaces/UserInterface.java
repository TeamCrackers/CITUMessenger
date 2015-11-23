package com.cebuinstituteoftechnology_university.citumessenger.Interfaces;

import com.cebuinstituteoftechnology_university.citumessenger.Models.User;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by osias on 11/23/2015.
 */
public interface UserInterface {
    @GET("/user/{userId}")
    public List<User> getUser(@Path("userId")String user_id);
    @POST("/user/login")
    public User login(@Body User user);
    @POST("/user/register")
    public User register(@Body User user);
}
