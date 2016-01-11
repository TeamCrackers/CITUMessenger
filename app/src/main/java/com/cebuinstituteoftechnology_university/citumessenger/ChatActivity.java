package com.cebuinstituteoftechnology_university.citumessenger;

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
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    private EditText composeMessage;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<Message> chatHistory;

    // CHAT
    private Socket socket;

    private void initializeConnectionAndListeners(){
        try {
            socket = IO.socket("http://192.168.43.10:3000/");
            socket.on("connect", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                     int n = 0;
                }
            });
            socket.on("receive message", new Emitter.Listener() {
                 @Override
                public void call(Object... args) {
                    System.out.print("");
                }
            });
            socket.on("disconnect", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e("INFO", "DISCONNECTED");
                }
            });
            socket.connect();
            socket.emit("userid", "HAHAHA IAN is GREAT");
            socket.emit("join room","testid");

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
