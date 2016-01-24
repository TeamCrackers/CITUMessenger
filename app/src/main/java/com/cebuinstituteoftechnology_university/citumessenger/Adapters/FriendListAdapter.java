package com.cebuinstituteoftechnology_university.citumessenger.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cebuinstituteoftechnology_university.citumessenger.Models.User;
import com.cebuinstituteoftechnology_university.citumessenger.R;
//import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Nelson on 1/23/2016.
 */
public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolderFriend> {

    private ArrayList<User> listFriends = new ArrayList<>();
    private LayoutInflater layoutInflater;
    //PARA NI SA PICTURE
    //private VolleySingleton volleySingleton;
    //private ImageLoader imageLoader;

    public FriendListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    public void setListFriends(ArrayList<User> listFriends) {
        this.listFriends = listFriends;
        notifyItemRangeChanged(0, listFriends.size());
    }

    @Override
    public ViewHolderFriend onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item_friend, parent, false);
        ViewHolderFriend viewHolder = new ViewHolderFriend(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderFriend holder, int position) {
        User currentFriend = listFriends.get(position);
        holder.friendName.setText(currentFriend.getFirstName());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolderFriend extends RecyclerView.ViewHolder {

        private ImageView friendThumbnail;
        private TextView friendName;
        public ViewHolderFriend(View itemView) {
            super(itemView);
            friendThumbnail = (ImageView) itemView.findViewById(R.id.imageView);
            friendName = (TextView) itemView.findViewById(R.id.firstName);
        }
    }
}
