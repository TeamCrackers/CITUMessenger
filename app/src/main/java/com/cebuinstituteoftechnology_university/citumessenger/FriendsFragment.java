package com.cebuinstituteoftechnology_university.citumessenger;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cebuinstituteoftechnology_university.citumessenger.Adapters.FriendListAdapter;
import com.cebuinstituteoftechnology_university.citumessenger.Models.Friends;
import com.cebuinstituteoftechnology_university.citumessenger.Models.User;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class FriendsFragment extends android.support.v4.app.Fragment {

    @Bind(R.id.friendList)
    RecyclerView friendList;
    RecyclerView.LayoutManager friendsManager;
    public FriendListAdapter friendListAdapter;

    public FriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        friendList.setHasFixedSize(true);
        friendsManager = new LinearLayoutManager(view.getContext());
        friendList.setLayoutManager(friendsManager);
        friendListAdapter  = new FriendListAdapter(new ArrayList<Friends>());
        friendList.setAdapter(friendListAdapter);
       //123 test();
        friendListAdapter.notifyDataSetChanged();
        super.onViewCreated(view, savedInstanceState);
    }

    public void clearFriendList(){
        friendListAdapter.clear();
    }
    public void addFriend(Friends friend){
        friendListAdapter.addNewFriend(friend);
        friendListAdapter.notifyDataSetChanged();
    }


}
