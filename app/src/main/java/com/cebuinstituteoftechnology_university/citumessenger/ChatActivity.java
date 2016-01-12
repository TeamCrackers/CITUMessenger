package com.cebuinstituteoftechnology_university.citumessenger;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cebuinstituteoftechnology_university.citumessenger.Adapters.ChatAdapter;
import com.cebuinstituteoftechnology_university.citumessenger.Models.Message;


import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatActivity extends AppCompatActivity {

    private EditText composeMessage;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<Message> chatHistory;

    // CHAT
    private Socket socket;
    private String ip = "192.168.56.1";
    private int port = 3000;
    private void initializeConnectionAndListeners(){
        try {
            socket = IO.socket("http://" + ip + ":" + port + "/");
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    socket.emit("userid", "HAHAHA IAN is GREAT");
                    socket.emit("join room","testid");

                }
            }).on("receive message", new Emitter.Listener() {
                 @Override
                public void call(Object... args) {
                     try {
                         final JSONObject json = (JSONObject)args[0];

                         runOnUiThread(new Runnable() {
                             @Override
                             public void run() {
                                 Message msg = new Message();
                                 try {
                                     msg.setMessage(json.getString("message"));
                                 } catch (JSONException e) {
                                     e.printStackTrace();
                                 }
                                 msg.setTimeStamp(new Date());
                                 displayMessage(msg);
                             }
                         });
                         Snackbar.make(findViewById(R.id.chatActvityRoot),"Message Received from:"+json.getString("user"), Snackbar.LENGTH_SHORT).show();
                     } catch (Exception e) {
                        e.printStackTrace();
                     }
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e("INFO", "DISCONNECTED");
                }
            }).on(Socket.EVENT_RECONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    String s = args.toString();
                }
            })
            ;
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initControls();
        initializeConnectionAndListeners();
    }

    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        composeMessage = (EditText) findViewById(R.id.composeMessage);
        sendBtn = (Button) findViewById(R.id.sendButton);

        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        loadDummyHistory();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = composeMessage.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                Message chatMessage = new Message();
                chatMessage.setId(123);//dummy
                chatMessage.setMessage(messageText);
                chatMessage.setTimeStamp(new Date());
                chatMessage.setUserId(100);

                composeMessage.setText("");

                displayMessage(chatMessage);
                // CHAT

                JSONObject json = new JSONObject();
                try {
                    json.put("message",messageText);
                    json.put("roomid","testid");
                    socket.emit("send message", json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void displayMessage(Message message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void loadDummyHistory(){

        chatHistory = new ArrayList<Message>();

        Message msg = new Message();
        msg.setId(1);
        msg.setMessage("Hi");
        msg.setTimeStamp(new Date());
        chatHistory.add(msg);
        Message msg1 = new Message();
        msg1.setId(2);
        msg1.setMessage("How are you?");
        msg1.setTimeStamp(new Date());
        chatHistory.add(msg1);
        Message msg2 = new Message();
        msg2.setId(3);
        msg2.setMessage("Chuya gud.");
        msg2.setTimeStamp(new Date());
        chatHistory.add(msg2);
        adapter = new ChatAdapter(ChatActivity.this, new ArrayList<Message>());
        messagesContainer.setAdapter(adapter);

        for(int i=0; i<chatHistory.size(); i++) {
            Message message = chatHistory.get(i);
            displayMessage(message);
        }
    }




}
