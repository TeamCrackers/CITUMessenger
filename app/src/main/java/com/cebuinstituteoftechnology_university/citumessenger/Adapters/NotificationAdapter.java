package com.cebuinstituteoftechnology_university.citumessenger.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cebuinstituteoftechnology_university.citumessenger.BackgroundServices.AuthenticationService;
import com.cebuinstituteoftechnology_university.citumessenger.BackgroundServices.ChatService;
import com.cebuinstituteoftechnology_university.citumessenger.Events.AuthenticationEvent;
import com.cebuinstituteoftechnology_university.citumessenger.HomeActivity;
import com.cebuinstituteoftechnology_university.citumessenger.Models.Conversation;
import com.cebuinstituteoftechnology_university.citumessenger.Models.Notification;
import com.cebuinstituteoftechnology_university.citumessenger.Models.Request;
import com.cebuinstituteoftechnology_university.citumessenger.Models.User;
import com.cebuinstituteoftechnology_university.citumessenger.R;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by osias on 1/28/2016.
 */
public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static int NOTIFICATION_TYPE = 0;
    public static int REQUEST_TYPE = 1;
    private List<Serializable> notifications;



    public void addNewNotification(Notification notification){
        notifications.add(0,notification);
        this.notifyDataSetChanged();
    }

    public void addRequest(Request request){
        notifications.add(0,request);
        this.notifyDataSetChanged();
    }

    public void removeNotification(int position){
        notifications.remove(position);
        this.notifyDataSetChanged();
    }
    public void removeRequest(Request request){
        notifications.remove(request);
        this.notifyDataSetChanged();
    }
    public NotificationAdapter(List<Serializable> notificationList){
        if(notificationList!=null)
            notifications = notificationList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if(viewType == REQUEST_TYPE) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_request_layout, parent, false);
            vh = new RequestViewHolder(v);
        }
        else
        {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_notification_layout, parent, false);
            vh = new NotificationViewHolder(v);
        }
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        Serializable selected = notifications.get(position);
        if(selected instanceof Request){
            return REQUEST_TYPE;
        }
        else
            return NOTIFICATION_TYPE;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Serializable selected = notifications.get(position);
        if(selected instanceof Notification){
            Notification currentNotification = (Notification)selected;
            ((NotificationViewHolder)holder).title.setText(currentNotification.getTitle());
            ((NotificationViewHolder)holder).description.setText(currentNotification.getDescription());
        }
        else if(selected instanceof Request){
            Request currentNotfication = (Request)selected;
            ((RequestViewHolder)holder).request = currentNotfication;
            User user = new User(null,null);
            user.setSchoolId(currentNotfication.getFrom_user_id());
            ((RequestViewHolder)holder).setUser(user);
        }
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class RequestViewHolder extends  RecyclerView.ViewHolder{
        @Bind(R.id.request_title)
        TextView title;
        Request request;
        Context context = null;
        public RequestViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.context = itemView.getContext();
            EventBus.getDefault().register(this);

        }

        public void setUser(User user){
            AuthenticationService.getUser(this.context,user);
        }

        public void onEvent(final AuthenticationEvent event){
            ((HomeActivity)this.context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (event.getMessage().contentEquals(AuthenticationEvent.USER_GET_SUCCESS)) {
                        User user = event.getUser();
                        if (user.getSchoolId().contentEquals(request.getFrom_user_id())) {
                            title.setText(user.getNickName());
                        }
                    }
                    EventBus.getDefault().unregister(RequestViewHolder.this);
                }
            });

        }



        @OnClick(R.id.request_accept)
        public void accept(){
            Conversation conversation = new Conversation();
            conversation.setId(request.getConversation_id());
            ChatService.joinConversation(this.context, conversation);
            NotificationAdapter.this.removeRequest(this.request);
        }
        @OnClick(R.id.request_decline)
        public void decline(){
            NotificationAdapter.this.removeRequest(this.request);
        }
    }

    public static class NotificationViewHolder extends  RecyclerView.ViewHolder{
        @Bind(R.id.notification_image)
        public ImageView image;
        @Bind(R.id.notification_title)
        public TextView title;
        @Bind(R.id.notification_description)
        public TextView description;

        Context currentContext= null;

        public NotificationViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            this.currentContext = v.getContext();
        }
        @OnClick(R.id.notification_card)
        public void notification_Clicked(){
            Toast.makeText(currentContext,title.getText().toString() + " was clicked!",Toast.LENGTH_SHORT).show();
        }
    }



}
