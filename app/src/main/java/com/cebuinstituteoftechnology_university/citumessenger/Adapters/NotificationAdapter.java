package com.cebuinstituteoftechnology_university.citumessenger.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cebuinstituteoftechnology_university.citumessenger.Models.Notification;
import com.cebuinstituteoftechnology_university.citumessenger.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by osias on 1/28/2016.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<Notification> notifications;



    public void addNewNotification(Notification notification){
        notifications.add(0,notification);
    }
    public NotificationAdapter(List<Notification> notificationList){
        if(notificationList!=null)
            notifications = notificationList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_notification_layout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notification currentNotification = notifications.get(position);
        holder.title.setText(currentNotification.getTitle());
        holder.description.setText(currentNotification.getDescription());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        @Bind(R.id.notification_image)
        public ImageView image;
        @Bind(R.id.notification_title)
        public TextView title;
        @Bind(R.id.notification_description)
        public TextView description;

        Context currentContext= null;

        public ViewHolder(View v) {
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
