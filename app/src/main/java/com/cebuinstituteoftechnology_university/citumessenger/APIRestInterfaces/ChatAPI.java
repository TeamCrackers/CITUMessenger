package com.cebuinstituteoftechnology_university.citumessenger.APIRestInterfaces;

import com.cebuinstituteoftechnology_university.citumessenger.Models.Conversation;
import com.cebuinstituteoftechnology_university.citumessenger.Models.Request;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by osias on 2/28/2016.
 */
public interface ChatAPI  {
    @POST("/conversation/")
    Call<Boolean> createConversation(@Body Conversation conversation);
    @GET("/conversation/{id}")
    Call<Conversation> getConversation(@Path("id")String id);
    @PUT("/conversation/")
    Call<Boolean> updateConversation(@Body Conversation conversation);
    @GET("/conversation/getAllConversation/{id}")
    Call<List<Conversation>> getAllConversation(@Path("id")String id);
    @POST("/conversation/request")
    Call<Boolean> sendRequest(@Body Request request);
    @GET("/conversation/getAllRequests/{userid}")
    Call<List<Request>> getAllRequests(@Path("userid")String id);
}
