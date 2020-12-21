package com.apps.kunalfarmah.Spo2Watcher.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.kunalfarmah.Spo2Watcher.model.ChatListUser;
import com.apps.kunalfarmah.Spo2Watcher.activity.ChatRoomActivity;
import com.apps.kunalfarmah.Spo2Watcher.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;



public class UserListAdapter extends FirebaseRecyclerAdapter<ChatListUser, UserListAdapter.MyUserHolder> {

    Context context;
    private static final int FAVOURITE = -1;
    private static final int NOT_FAVOURITE = 0;

    /**
     * @param1      Firebase will marshall the data at a location into
     *                        an instance of a class that you provide
     * @param2 modelLayout     This is the layout used to represent a single item in the list.
     *                        You will be responsible for populating an instance of the corresponding
     *                        view with the data from an instance of modelClass.
     * @param3 The class that hold references to all sub-views in an instance modelLayout.
     * @param4 ref             The Firebase location to watch for data changes. Can also be a slice of a location,
     *                        using some combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
     */
    public UserListAdapter(int modelLayout, Query ref, Context context) {
        super(ChatListUser.class, modelLayout, MyUserHolder.class, ref);
        this.context = context;

    }

    @NonNull
    @Override
    public MyUserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout, parent, false);
        return new MyUserHolder(view);
    }

    @Override
    protected void populateViewHolder(MyUserHolder viewHolder, final ChatListUser model, int position) {
        model.reverseTime();

        viewHolder.userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int favourite;
                if (model.getFavourite()== FAVOURITE){
                    favourite = NOT_FAVOURITE;
                    Toast.makeText(context, "Removed "+model.getUserName()+ " from favourites.", Toast.LENGTH_SHORT).show();
                }
                else {
                    favourite = FAVOURITE;
                    Toast.makeText(context, "Added "+model.getUserName()+" to favourites!", Toast.LENGTH_SHORT).show();
                }
                FirebaseDatabase.getInstance().getReference()
                        .child("chats")
                        .child(FirebaseAuth.getInstance().getUid())
                        .child("chat_list")
                        .child(model.getUserID())
                        .child("favourite")
                        .setValue(favourite);
            }
        });
        viewHolder.userName.setText(model.getUserName());
        viewHolder.userMessage.setText(model.getLastMessage());
        viewHolder.userTime.setText(DateFormat.format("h:mm a", model.getLastMessageTime()));
        if(model.getUnreadMessages()){
            viewHolder.unreadMessages.setVisibility(View.VISIBLE);
            viewHolder.userTime.setTextColor(context.getResources().getColor(R.color.timeColor));
            viewHolder.userMessage.setTypeface(viewHolder.userMessage.getTypeface(), Typeface.BOLD);
        }
        else{
            viewHolder.unreadMessages.setVisibility(View.INVISIBLE);
            viewHolder.userTime.setTextColor(context.getResources().getColor(android.R.color.tab_indicator_text));
            viewHolder.userMessage.setTypeface(null, Typeface.NORMAL);
        }
        if(model.isOnline()){
            viewHolder.online.setVisibility(View.VISIBLE);
        }
        else{
            viewHolder.online.setVisibility(View.GONE);
        }

        if(model.getFavourite()==FAVOURITE){
            viewHolder.favourite.setVisibility(View.VISIBLE);
        }
        else{
            viewHolder.favourite.setVisibility(View.GONE);
        }
    }


    class MyUserHolder extends RecyclerView.ViewHolder{

        TextView userName, userMessage, userTime;
        ImageView unreadMessages, online, favourite, userIcon;

        MyUserHolder(@NonNull final View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            userMessage = itemView.findViewById(R.id.user_message);
            userTime = itemView.findViewById(R.id.user_time);
            unreadMessages = itemView.findViewById(R.id.unread);
            online = itemView.findViewById(R.id.online_label);
            favourite = itemView.findViewById(R.id.favourite);
            userIcon = itemView.findViewById(R.id.icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ChatRoomActivity.class);
                    intent.putExtra("user_uid", getItem(getAdapterPosition()).getUserID());
                    intent.putExtra("user_name", getItem(getAdapterPosition()).getUserName());
                    v.getContext().startActivity(intent);
                }
            });

        }
    }
}
