package com.cebuinstituteoftechnology_university.citumessenger.BackgroundServices;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cebuinstituteoftechnology_university.citumessenger.APIRestInterfaces.UserAPI;
import com.cebuinstituteoftechnology_university.citumessenger.Config.AppConfig;
import com.cebuinstituteoftechnology_university.citumessenger.HomeActivity;
import com.cebuinstituteoftechnology_university.citumessenger.Models.Conversation;
import com.cebuinstituteoftechnology_university.citumessenger.Models.Message;
import com.cebuinstituteoftechnology_university.citumessenger.Models.User;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

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

    public static final String SERVER_IP_ADDRESS = "192.168.1.6";
    public static final String SERVER_PORT = "3000";

    public static final String EXTRA_MESSAGE = "Message Extra";
    public static final String EXTRA_CONVERSATION = "Conversation Extra";
    public static Socket socket;
    private static Emitter.Listener receiveMessage;
    private static Emitter.Listener disconnect;
    private static Emitter.Listener connect;
    private static Emitter.Listener reconnect;
    private static UserAPI userAPI;
    private static EventBus eventBus;
    public ChatService() {
        super(NAME);
        eventBus = EventBus.getDefault();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.host + ":" + AppConfig.port + "")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userAPI = retrofit.create(UserAPI.class);
    }

    public void initializeSocket(User user) throws URISyntaxException
    {
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

    public static void joinConversation(Conversation conversation){
        try {
            Response<User> response = userAPI.getUser(HomeActivity.CURRENT_USER.getSchoolId()).execute();
            User user = response.body();
            boolean ok = true;
            if(user.getConversations() == null)
                user.setConversations(new ArrayList<String>());
            if(!user.getConversations().contains(conversation.getId()))
            {
                user.getConversations().add(conversation.getId());
                    ok = userAPI.updateUser(user).execute().body();
            }
            if(ok)
                socket.emit(EVENT_START_CONVERSATION,conversation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent!=null)
        {
            final String action = intent.getAction();
            if(action.contentEquals(ChatService.EVENT_SEND_MESSAGE))
                sendMessage((Message)intent.getSerializableExtra(EXTRA_MESSAGE));
            else if(action.contentEquals(ChatService.EVENT_START_CONVERSATION))
                joinConversation((Conversation)intent.getSerializableExtra(EXTRA_CONVERSATION));
        }
    }

    public static void startConversation(Context context,Conversation conversation) {
        Intent intent = new Intent(context,ChatService.class);
        intent.setAction(EVENT_START_CONVERSATION);
        intent.putExtra(EXTRA_CONVERSATION,conversation);
        context.startService(intent);

    }
}
