package com.cebuinstituteoftechnology_university.citumessenger.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cebuinstituteoftechnology_university.citumessenger.BackgroundServices.ChatService;
import com.cebuinstituteoftechnology_university.citumessenger.ChatActivity;
import com.cebuinstituteoftechnology_university.citumessenger.HomeActivity;
import com.cebuinstituteoftechnology_university.citumessenger.Models.Conversation;
import com.cebuinstituteoftechnology_university.citumessenger.Models.User;
import com.cebuinstituteoftechnology_university.citumessenger.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by osias on 2/29/2016.
 */
public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder> {
    private List<Conversation> conversations;

    public InboxAdapter() {
        conversations = new ArrayList<>();
    }

    public void addConversation(Conversation conversation){
        if(conversations!=null) {
            conversations.add(0, conversation);
            this.notifyDataSetChanged();
        }
    }

    public void updateConversation(Conversation conversation){
        this.removeConversation(conversation);
        this.addConversation(conversation);
    }

    public void removeConversation(Conversation conversation){
        for(int i=0;i<conversations.size();i++){
            if(conversations.get(i).getId().equals(conversation.getId())){
                conversations.remove(i);
                this.notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_inbox_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Conversation selectedConversation = conversations.get(position);
        if(selectedConversation!=null) {
            String title = "";
            if (HomeActivity.CURRENT_USER != null)
                for (User user : selectedConversation.getParticipants()) {
                    if (!user.getSchoolId().equals(HomeActivity.CURRENT_USER.getSchoolId())) {
                        title = user.getNickName();
                    }
                }
            if (selectedConversation.getParticipants().size() > 2)
                title += "(+" + (selectedConversation.getParticipants().size() - 2) + ")";
            if (title != null)
                holder.title.setText(title);

            holder.date.setText(selectedConversation.getRecentUpdate().toString());
            holder.setConversationControls(selectedConversation);
        }

    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    public void clear() {
        this.conversations.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public String conversationId;
        @Bind(R.id.inbox_image)
        public CircleImageView image;
        @Bind(R.id.inbox_title)
        public TextView title;
        @Bind(R.id.inbox_date)
        public TextView date;
        @Bind(R.id.inbox_leave)
        public ImageButton leaveButton;
        @Bind(R.id.inbox_card)
        CardView inboxCard;
        Context currentContext= null;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            this.currentContext = v.getContext();
        }
        public void setConversationControls(final Conversation conversation){
            leaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChatService.leaveConversation(v.getContext(),conversation);
                }
            });
            inboxCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChatService.joinConversation(v.getContext(),conversation);
                    ChatActivity.startChatActivity(v,conversation);
                }
            });

        }
    }
}
