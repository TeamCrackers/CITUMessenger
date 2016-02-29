package com.cebuinstituteoftechnology_university.citumessenger;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cebuinstituteoftechnology_university.citumessenger.Adapters.NotificationAdapter;
import com.cebuinstituteoftechnology_university.citumessenger.BackgroundServices.ChatService;
import com.cebuinstituteoftechnology_university.citumessenger.Models.Notification;
import com.cebuinstituteoftechnology_university.citumessenger.Models.Request;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


public class NotificationsFragment extends android.support.v4.app.Fragment {

    @Bind(R.id.notificationList)
    public RecyclerView notificationList;
    public RecyclerView.LayoutManager notificationManager;
    public NotificationAdapter notificationAdapter;
    public NotificationsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EventBus.getDefault().register(this);
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        notificationList.setHasFixedSize(true);
        notificationManager = new LinearLayoutManager(view.getContext());
        notificationList.setLayoutManager(notificationManager);
        notificationAdapter  = new NotificationAdapter(new ArrayList<Serializable>());
        notificationList.setAdapter(notificationAdapter);
        ChatService.getAllRequest(this.getContext());
        //test_SampleData();
        super.onViewCreated(view, savedInstanceState);
    }

    private void test_SampleData(){
        Notification sample = new Notification();
        sample.setTitle("Ian is great");
        sample.setDescription("Nice ka one");
        Notification sample2 = new Notification();
        sample2.setTitle("Chuya recycler oi");
        sample2.setDescription("Oh mao na");

        notificationAdapter.addNewNotification(sample);
        notificationAdapter.addNewNotification(sample2);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(this,v);
        return v;

    }




}
