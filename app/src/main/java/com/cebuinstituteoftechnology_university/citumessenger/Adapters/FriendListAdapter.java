package com.cebuinstituteoftechnology_university.citumessenger.Adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cebuinstituteoftechnology_university.citumessenger.BackgroundServices.ChatService;
import com.cebuinstituteoftechnology_university.citumessenger.ChatActivity;
import com.cebuinstituteoftechnology_university.citumessenger.HomeActivity;
import com.cebuinstituteoftechnology_university.citumessenger.Models.Conversation;
import com.cebuinstituteoftechnology_university.citumessenger.Models.Friends;
import com.cebuinstituteoftechnology_university.citumessenger.R;
//import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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
        holder.friend = currentFriend;
        holder.friendName.setText(currentFriend.getUserInfo().getNickName());

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
        public Friends friend;

        Context currentContext = null;

        public ViewHolderFriend(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.currentContext = itemView.getContext();
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Snackbar snack = Snackbar.make((View) v.getParent(), "Start converstaion with " + friendName.getText().toString(), Snackbar.LENGTH_SHORT);
                    snack.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Conversation conversation = new Conversation();
                            conversation.addParticipant(HomeActivity.CURRENT_USER);
                            conversation.addParticipant(friend.getUserInfo());
                            ChatActivity.startChatActivity(v,conversation );
                        }
                    });
                    snack.show();
                }
            });
        }
    }
}
