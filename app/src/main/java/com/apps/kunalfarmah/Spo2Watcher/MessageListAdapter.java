package com.apps.kunalfarmah.Spo2Watcher;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;

public class MessageListAdapter extends FirebaseRecyclerAdapter<ChatMessage,MessageListAdapter.MyMessageHolder> {

    private static final String myUID = FirebaseAuth.getInstance().getUid();
    private static final int MY_MESSAGE = 0;
    private static final int OTHER_MESSAGE = 1;

    /**
     *                        an instance of a class that you provide
     * @param modelLayout     This is the layout used to represent a single item in the list.
     *                        You will be responsible for populating an instance of the corresponding
     *                        view with the data from an instance of modelClass.
     * @param ref             The Firebase location to watch for data changes. Can also be a slice of a location,
     *                        using some combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
     */
    MessageListAdapter(int modelLayout, Query ref) {
        super(ChatMessage.class, modelLayout, MyMessageHolder.class, ref);
    }

    @Override
    protected void populateViewHolder(MyMessageHolder viewHolder, ChatMessage model, int position) {

        // Set their text
        viewHolder.messageBody.setText(model.getMessageText());
        viewHolder.messageUser.setText(model.getMessageUser().getUserName());

        // Format the date before showing it
        viewHolder.messageTime.setText(DateFormat.format("h:mm a", model.getMessageTime()));
    }

    @NonNull
    @Override
    public MyMessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if(viewType == MY_MESSAGE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_message, parent, false);
            return new MyMessageHolder(view);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.other_message, parent, false);
            return new MyMessageHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = getItem(position);

        if(message.getMessageUser().getUserID().equals(myUID)){
            Log.i("MyLogs", message.getMessageUser().getUserName() +" "+ message.getMessageUser().getUserID());
            return MY_MESSAGE;
        }
        else{
            return OTHER_MESSAGE;
        }
    }

    class MyMessageHolder extends RecyclerView.ViewHolder{

        TextView messageBody, messageUser, messageTime;

        MyMessageHolder(@NonNull View itemView) {
            super(itemView);
            switch (itemView.getId()){
                case R.id.my_message: messageBody = itemView.findViewById(R.id.message_text);
                                         messageUser = itemView.findViewById(R.id.message_user);
                                         messageTime = itemView.findViewById(R.id.message_time);
                                         break;
                case R.id.other_message: messageBody = itemView.findViewById(R.id.message_text_other);
                                            messageUser = itemView.findViewById(R.id.message_user_other);
                                            messageTime = itemView.findViewById(R.id.message_time_other);
                                            break;
            }
        }
    }

}
