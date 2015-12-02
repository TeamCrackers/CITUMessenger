package com.cebuinstituteoftechnology_university.citumessenger.Interfaces;

import com.cebuinstituteoftechnology_university.citumessenger.Models.User;

import org.json.JSONObject;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by osias on 11/23/2015.
 */
public interface UserService {
    @GET("/user/ {userId}")
     Call<List<User>> getUser(@Path("userId")String user_id);
    @POST("/user//login")
     Call<User> login(@Body User user);
    @POST("/user/register")
     Call<User> register(@Body User user);

    @GET("/user/test")
     Call<User> test();
}
