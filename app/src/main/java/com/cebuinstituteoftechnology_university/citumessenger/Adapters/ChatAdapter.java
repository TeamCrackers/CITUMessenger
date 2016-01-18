package com.cebuinstituteoftechnology_university.citumessenger.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cebuinstituteoftechnology_university.citumessenger.Models.Message;
import com.cebuinstituteoftechnology_university.citumessenger.R;

import java.util.List;
import java.util.Random;

/**
 * Created by Andrew Paul Mago on 12/2/2015.
 */
public class ChatAdapter extends BaseAdapter {

    private List<Message> chatHistory;
    private Activity context;
    private String userId;

    public ChatAdapter(Activity context, List<Message> chatHistory) {
        this.context = context;
        this.chatHistory = chatHistory;
    }

    public ChatAdapter(Activity context, List<Message> chatHistory, String id) {
        this.context = context;
        this.chatHistory = chatHistory;
        this.userId = id;
    }

    @Override
    public int getCount() {
        if(chatHistory!=null)
            return chatHistory.size();
        else
            return 0;
    }

    @Override
    public Message getItem(int position) {
        if(chatHistory!=null)
            return chatHistory.get(position);
        else
            return null;
    }

    @Override
    public long getItemId(int position) {
        return chatHistory.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Message chatMessage = getItem(position);
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = vi.inflate(R.layout.list_item_message, null);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //to simulate whether it me or other sender
        boolean chatPosition = (chatMessage.getUserId().contentEquals(userId) ?
                                        true : false);
        setAlignment(holder, chatPosition);
        holder.chatMessage.setText(chatMessage.getMessage());
        holder.chatInfo.setText(chatMessage.getTimeStamp().toString());

        return convertView;
    }

    public void add(Message message) {
        chatHistory.add(message);
    }

    public void add(List<Message> messages) {
        chatHistory.addAll(messages);
    }

    private void setAlignment(ViewHolder holder, boolean isMe) {
        if (isMe) {
            holder.contentWithBG.setBackgroundResource(R.drawable.chatbubble_sender);

            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.contentWithBG.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.content.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) holder.chatMessage.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.chatMessage.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) holder.chatInfo.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.chatInfo.setLayoutParams(layoutParams);
        } else {
            holder.contentWithBG.setBackgroundResource(R.drawable.chatbubble_receiver);

            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.contentWithBG.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            holder.content.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) holder.chatMessage.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.chatMessage.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) holder.chatInfo.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.chatInfo.setLayoutParams(layoutParams);
        }
    }

    private ViewHolder createViewHolder(View v) {
        ViewHolder holder = new ViewHolder();
        holder.chatMessage = (TextView) v.findViewById(R.id.chatMessage);
        holder.content = (LinearLayout) v.findViewById(R.id.content);
        holder.contentWithBG = (LinearLayout) v.findViewById(R.id.contentWithBackground);
        holder.chatInfo = (TextView) v.findViewById(R.id.chatInfo);
        return holder;
    }

    private static class ViewHolder {
        public TextView chatMessage;
        public TextView chatInfo;
        public LinearLayout content;
        public LinearLayout contentWithBG;
    }
}
