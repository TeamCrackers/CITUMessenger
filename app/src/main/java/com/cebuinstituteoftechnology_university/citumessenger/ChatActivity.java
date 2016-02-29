package com.cebuinstituteoftechnology_university.citumessenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cebuinstituteoftechnology_university.citumessenger.Adapters.ChatAdapter;
import com.cebuinstituteoftechnology_university.citumessenger.BackgroundServices.AuthenticationService;
import com.cebuinstituteoftechnology_university.citumessenger.BackgroundServices.ChatService;
import com.cebuinstituteoftechnology_university.citumessenger.Events.AuthenticationEvent;
import com.cebuinstituteoftechnology_university.citumessenger.Models.Conversation;
import com.cebuinstituteoftechnology_university.citumessenger.Models.Message;
import com.cebuinstituteoftechnology_university.citumessenger.Models.User;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class ChatActivity extends AppCompatActivity {
    public static Conversation CURRENT_CONVERSATION;
    public static  final String EXTRA_CONVERSATION = "Conversation";
    public static void startChatActivity(View v,Conversation conversation){
        ContextThemeWrapper activity = (ContextThemeWrapper) v.getContext();
        Intent intent = new Intent(activity,ChatActivity.class);
        intent.putExtra(EXTRA_CONVERSATION, conversation);
        CURRENT_CONVERSATION = conversation;
        activity.startActivity(intent);
    }
    @Bind(R.id.composeMessage)
    public EditText composeMessage;
    @Bind(R.id.messagesContainer)
    public ListView messagesContainer;
    @Bind(R.id.sendButton)
    public Button sendBtn;
    @Bind(R.id.right_drawer_child)
    public ListView roomParticipants;

    private ChatAdapter adapter;
    private ArrayList<Message> chatHistory;

    @OnClick(R.id.addPeople)
    public void addPeople() {
        startActivity(new Intent(this, AddPeopleActivity.class));
    }

    @OnClick(R.id.leaveRoom)
    public void leaveRoom() {
        this.finish();
    }

    // CHAT
    public String user1;
    public String user2;
    public ChatService chatService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        Bundle bundle = this.getIntent().getExtras();
        CURRENT_CONVERSATION = (Conversation)bundle.getSerializable(EXTRA_CONVERSATION);
        EventBus.getDefault().register(this);
        adapter = new ChatAdapter(ChatActivity.this, new ArrayList<Message>(),HomeActivity.CURRENT_USER.getSchoolId());
        messagesContainer.setAdapter(adapter);
        roomParticipants.setAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1));
        getParticipants();
        try {
            chatService = new ChatService();
            chatService.initializeSocket(HomeActivity.CURRENT_USER);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void getParticipants(){
        for(User user: CURRENT_CONVERSATION.getParticipants()){
            AuthenticationService.getUser(this, user);
        }
    }

    public void onEvent(final AuthenticationEvent event){
        if(event.getMessage() == AuthenticationEvent.USER_GET_SUCCESS)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ArrayAdapter adapter = (ArrayAdapter) roomParticipants.getAdapter();
                    adapter.add(event.getUser().getNickName());
                    adapter.notifyDataSetChanged();
                }
            });

        }
    }
    public void onEvent(Message message){
        displayMessage(message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Snackbar.make(messagesContainer,user1 + " "+ user2,Snackbar.LENGTH_SHORT).show();
    }
    @OnClick(R.id.sendButton)
    public void sendMessage()
    {
        Message message = new Message();
        message.setMessage(composeMessage.getText().toString());
        message.setTimeStamp(new Date());
        message.setUserId(HomeActivity.CURRENT_USER.getSchoolId());
        chatService.sendMessage(message);
    }

    public void displayMessage(Message message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        EventBus.getDefault().unregister(this);
    }
}
