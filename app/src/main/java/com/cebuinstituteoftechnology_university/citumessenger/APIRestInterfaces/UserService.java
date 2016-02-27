package com.cebuinstituteoftechnology_university.citumessenger.APIRestInterfaces;

import com.cebuinstituteoftechnology_university.citumessenger.Models.User;

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
    @GET("/user/{schoolId}")
     Call<User> getUser(@Path("schoolId")String user_id);
    @POST("/user/login")
     Call<User> login(@Body User user);
    @POST("/user/register")
     Call<Boolean> register(@Body User user);
    @GET("/user/test")
     Call<User> test();

}
