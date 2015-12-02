package com.cebuinstituteoftechnology_university.citumessenger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cebuinstituteoftechnology_university.citumessenger.Adapters.ChatAdapter;
import com.cebuinstituteoftechnology_university.citumessenger.Models.Message;

import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    private EditText composeMessage;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<Message> chatHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initControls();
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

        adapter = new ChatAdapter(ChatActivity.this, new ArrayList<Message>());
        messagesContainer.setAdapter(adapter);

        for(int i=0; i<chatHistory.size(); i++) {
            Message message = chatHistory.get(i);
            displayMessage(message);
        }
    }




}
