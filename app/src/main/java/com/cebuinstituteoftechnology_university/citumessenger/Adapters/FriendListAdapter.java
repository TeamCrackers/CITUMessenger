package com.cebuinstituteoftechnology_university.citumessenger.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cebuinstituteoftechnology_university.citumessenger.Models.Friends;
import com.cebuinstituteoftechnology_university.citumessenger.Models.User;
import com.cebuinstituteoftechnology_university.citumessenger.R;
//import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Nelson on 1/23/2016.
 */
public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolderFriend> {

    private List<Friends> friendList;

    public FriendListAdapter(List<Friends> friends) {
        if(friends != null) {
            friendList = friends;
        }
    }

    public void addNewFriend(Friends friend) {
        friendList.add(0, friend);
    }

    @Override
    public ViewHolderFriend onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_friend, parent, false);
        ViewHolderFriend viewHolder = new ViewHolderFriend(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderFriend holder, int position) {
        Friends currentFriend = friendList.get(position);
        holder.friendName.setText(currentFriend.getName());

    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    public void clear() {
        friendList.clear();
    }

    static class ViewHolderFriend extends RecyclerView.ViewHolder {
        @Bind(R.id.friend_avatar)
        public ImageView friendThumbnail;
        @Bind(R.id.friend_name)
        public TextView friendName;

        Context currentContext = null;

        public ViewHolderFriend(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.currentContext = itemView.getContext();
        }
    }
}
