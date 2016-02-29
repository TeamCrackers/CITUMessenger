package com.cebuinstituteoftechnology_university.citumessenger.BackgroundServices;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cebuinstituteoftechnology_university.citumessenger.APIRestInterfaces.ChatAPI;
import com.cebuinstituteoftechnology_university.citumessenger.APIRestInterfaces.UserAPI;
import com.cebuinstituteoftechnology_university.citumessenger.Config.AppConfig;
import com.cebuinstituteoftechnology_university.citumessenger.HomeActivity;
import com.cebuinstituteoftechnology_university.citumessenger.Models.Conversation;
import com.cebuinstituteoftechnology_university.citumessenger.Models.Message;
import com.cebuinstituteoftechnology_university.citumessenger.Models.Request;
import com.cebuinstituteoftechnology_university.citumessenger.Models.User;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by osias on 2/28/2016.
 */
public class ChatService extends IntentService{
    public static final String NAME = "Chat Service";

    public static final String EVENT_RECEIVE_MESSAGE = "receive message";
    public static final String EVENT_SEND_MESSAGE = "send message";
    public static final String EVENT_START_CONVERSATION = "join room";
    public static final String EVENT_LOAD_CONVERSATIONS ="Load Conversations" ;
    public static final String EVENT_REQUEST_JOIN = "request to join";
    public static final String EVENT_GET_REQUESTS = "get all requests";

    public static final String SERVER_IP_ADDRESS = "192.168.1.6";
    public static final String SERVER_PORT = "3000";

    public static final String EXTRA_MESSAGE = "Message Extra";
    public static final String EXTRA_CONVERSATION = "Conversation Extra";
    public static final String EXTRA_USER = "User extra";
    public static final String EXTRA_REQUEST = "Reuqest extra";

    public static Socket socket;
    private static Emitter.Listener receiveMessage;
    private static Emitter.Listener disconnect;
    private static Emitter.Listener connect;
    private static Emitter.Listener reconnect;
    private static UserAPI userAPI;
    private static ChatAPI chatAPI;
    private static EventBus eventBus;
    public ChatService() {
        super(NAME);
        eventBus = EventBus.getDefault();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.host + ":" + AppConfig.port + "")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userAPI = retrofit.create(UserAPI.class);
        chatAPI = retrofit.create(ChatAPI.class);
    }

    public void initializeSocket(User user) throws URISyntaxException{
        if(socket == null || !socket.connected()) {
            socket = IO.socket("http://" + SERVER_IP_ADDRESS + ":" + SERVER_PORT + "/");
            setReceiveMessage();
            setDisconnect();
            setReconnect();
            setConnct();
            socket.on(Socket.EVENT_CONNECT, connect);
            socket.on(Socket.EVENT_DISCONNECT, disconnect);
            socket.on(Socket.EVENT_RECONNECT, reconnect);
            socket.on(EVENT_RECEIVE_MESSAGE, receiveMessage);
            socket.connect();
        }
    }

    private static void setConnct(){
        connect = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.i(NAME, Socket.EVENT_CONNECT);
            }
        };
    }
    private static void setReceiveMessage(){
        receiveMessage = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Message message = new Message();
                ChatService.eventBus.post(message);
            }
        };
    }
    private static void setReconnect(){
        reconnect = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.i(NAME, Socket.EVENT_RECONNECT);
            }
        };
    }
    private static void setDisconnect(){
        disconnect = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.i(NAME,Socket.EVENT_DISCONNECT);
            }
        };
    }

    public static boolean sendMessage(Message message){
        boolean result = false;
        if(socket!=null &&socket.connected()) {
            socket.emit(EVENT_SEND_MESSAGE, message);
            result = true;
        }
        return result;
    }

    private static void joinConversation(Conversation conversation){
        try {
            Response<User> response = userAPI.getUser(HomeActivity.CURRENT_USER.getSchoolId()).execute();
            if(response.body()!=null) {
                boolean ok;
                if (chatAPI.getConversation(conversation.getId()).execute().body() != null) {
                    chatAPI.createConversation(conversation);
                }
                conversation = chatAPI.getConversation(conversation.getId()).execute().body();
                if(!conversation.getParticipants().contains(HomeActivity.CURRENT_USER))
                    conversation.addParticipant(HomeActivity.CURRENT_USER);
                ok = chatAPI.updateConversation(conversation).execute().body();
                if (ok)
                    socket.emit(EVENT_START_CONVERSATION, conversation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void requestToJoin(Request request){
        try {
            if(chatAPI.sendRequest(request).execute().body()){
                EventBus.getDefault().post(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void getAllRequests(User user)
    {
        try {
            List<Request> requests = chatAPI.getAllRequests(user.getSchoolId()).execute().body();
            eventBus.post(requests);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void loadConversations(User user){
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent!=null)
        {
            final String action = intent.getAction();
            if(action.contentEquals(ChatService.EVENT_SEND_MESSAGE))
                sendMessage((Message)intent.getSerializableExtra(EXTRA_MESSAGE));
            else if(action.contentEquals(ChatService.EVENT_START_CONVERSATION))
                joinConversation(((Conversation)intent.getSerializableExtra(EXTRA_CONVERSATION)));
            else if(action.contentEquals(ChatService.EVENT_LOAD_CONVERSATIONS))
                loadConversations((User)intent.getSerializableExtra(EXTRA_USER));
            else if(action.contentEquals(ChatService.EVENT_REQUEST_JOIN))
                requestToJoin((Request)intent.getSerializableExtra(EXTRA_REQUEST));
            else if(action.contentEquals(ChatService.EVENT_GET_REQUESTS))
                getAllRequests((User)intent.getSerializableExtra(EXTRA_USER));
        }
    }

    public static void getAllRequest(Context context){
        Intent intent = new Intent(context,ChatService.class);
        intent.setAction(EVENT_GET_REQUESTS);
        intent.putExtra(EXTRA_USER,HomeActivity.CURRENT_USER);
        context.startService(intent);
    }

    public static void joinConversation(Context context,Conversation conversation) {
        Intent intent = new Intent(context,ChatService.class);
        intent.setAction(EVENT_START_CONVERSATION);
        intent.putExtra(EXTRA_CONVERSATION,conversation);
        context.startService(intent);

    }

    public static void startLoadingAllConversations(Context context,User user){
        Intent intent = new Intent(context,ChatService.class);
        intent.setAction(EVENT_LOAD_CONVERSATIONS);
        intent.putExtra(EXTRA_USER, user);
        context.startService(intent);

    }

    public static void sendRequestToJoin(Context context, Request request){
        Intent intent = new Intent(context,ChatService.class);
        intent.setAction(EVENT_REQUEST_JOIN);
        intent.putExtra(EXTRA_REQUEST, request);
        context.startService(intent);
    }

    public static void leaveConversation(Conversation selectedConversation) {

    }
}
