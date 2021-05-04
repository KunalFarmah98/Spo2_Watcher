package com.apps.kunalfarmah.Spo2Watcher.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;

import com.apps.kunalfarmah.Spo2Watcher.model.ChatListUser;
import com.apps.kunalfarmah.Spo2Watcher.model.ChatMessage;
import com.apps.kunalfarmah.Spo2Watcher.model.MessageUser;
import com.apps.kunalfarmah.Spo2Watcher.R;
import com.apps.kunalfarmah.Spo2Watcher.adapter.MessageListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class ChatRoomActivity extends AppCompatActivity {

    private String myUid;
    private String userUid;
    private RecyclerView listOfMessages;
    private MessageListAdapter adapter;
    private MessageUser userData;
    private MessageUser myData;
    private int uFavourite, mFavourite;


    @Override
    protected void onResume() {
        super.onResume();

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        dbRef.child("chats").child(myUid)
                .child("chat_list")
                .child(userUid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            FirebaseDatabase.getInstance().getReference()
                                    .child("chats")
                                    .child(myUid)
                                    .child("chat_list")
                                    .child(userUid)
                                    .child("unreadMessages")
                                    .setValue(false);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        setUserOnlineState(true);

    }

    @Override
    protected void onPause() {
        super.onPause();

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        dbRef.child("chats").child(myUid)
                .child("chat_list")
                .child(userUid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            FirebaseDatabase.getInstance().getReference()
                                    .child("chats")
                                    .child(myUid)
                                    .child("chat_list")
                                    .child(userUid)
                                    .child("unreadMessages")
                                    .setValue(false);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        setUserOnlineState(false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        myUid = FirebaseAuth.getInstance().getUid();
        userUid = getIntent().getStringExtra("user_uid");

        final String userName = getIntent().getStringExtra("user_name");
        Objects.requireNonNull(getSupportActionBar()).setTitle(userName);
//        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");

        ref.orderByChild("userID").equalTo(userUid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot activitySnapShot : dataSnapshot.getChildren()) {
                                userData = activitySnapShot.getValue(MessageUser.class);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        ref.orderByChild("userID").equalTo(myUid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot activitySnapShot : dataSnapshot.getChildren()) {
                                myData = activitySnapShot.getValue(MessageUser.class);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = findViewById(R.id.input);
                final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
                // Read the input field and push a new instance of ChatMessage to the Firebase database
                final String message = input.getText().toString();
                if (!message.equals("")) {

                    sendMessageToChats(myUid, userUid, message);
                    addMessageToMyChatList(message);
                    addMessageToUserChatList(message);

                    // Clear the input
                    input.setText("");

                }
            }
        });

        displayChatMessages();
    }


    private void displayChatMessages() {

        listOfMessages = findViewById(R.id.list_of_messages);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("chats")
                .child(myUid)
                .child(userUid)
                .limitToLast(50);

        adapter = new MessageListAdapter(R.layout.my_message, query);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
        });
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = adapter.getItemCount();
                int lastVisiblePosition =
                        layoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    listOfMessages.scrollToPosition(positionStart);
                }
            }
        });
        listOfMessages.setAdapter(adapter);

        listOfMessages.setLayoutManager(layoutManager);
    }

    public void setUserOnlineState(final boolean state) {
        Boolean bool = state;
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("online").setValue(bool);

        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference chatRef = dbRef
                .child("chats")
                .child(uid)
                .child("chat_list");



        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> chatList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    chatList.add(snapshot.getKey());
                }
                for (String userUid : chatList) {
                    dbRef
                            .child("chats")
                            .child(userUid)
                            .child("chat_list")
                            .child(uid)
                            .child("online")
                            .setValue(state);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendMessageToChats(String from, String to, String message) {
//        Log.i("MyLOgs", myData.getUserName() + " "+myData.getEmailID());
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child("chats")
                .child(from)
                .child(to)
                .push()
                .setValue(new ChatMessage(message, myData));

        dbRef.child("chats")
                .child(to)
                .child(from)
                .push()
                .setValue(new ChatMessage(message, myData));

    }

    private void addMessageToMyChatList(final String message) {
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        dbRef.child("chats")
                .child(myUid)
                .child("chat_list")
                .child(userUid)
                .child("favourite")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            mFavourite = dataSnapshot.getValue(Integer.class);
                            dbRef.child("users")
                                    .child(userUid)
                                    .child("online")
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                boolean online = dataSnapshot.getValue(Boolean.class);
                                                dbRef.child("chats")
                                                        .child(myUid)
                                                        .child("chat_list")
                                                        .child(userUid)
                                                        .setValue(new ChatListUser(userData, -1 * new Date().getTime()
                                                                , message, false, online, mFavourite));
                                            } else {
                                                dbRef.child("chats")
                                                        .child(myUid)
                                                        .child("chat_list")
                                                        .child(userUid)
                                                        .setValue(new ChatListUser(userData, -1 * new Date().getTime()
                                                                , message, mFavourite));
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                        }
                        else {
                            dbRef.child("users")
                                    .child(userUid)
                                    .child("online")
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                boolean online = dataSnapshot.getValue(Boolean.class);
                                                dbRef.child("chats")
                                                        .child(myUid)
                                                        .child("chat_list")
                                                        .child(userUid)
                                                        .setValue(new ChatListUser(userData, -1 * new Date().getTime()
                                                                , message, false, online, 0));
                                            } else {
                                                dbRef.child("chats")
                                                        .child(myUid)
                                                        .child("chat_list")
                                                        .child(userUid)
                                                        .setValue(new ChatListUser(userData, -1 * new Date().getTime()
                                                                , message, 0));
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void addMessageToUserChatList(final String message) {
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        dbRef.child("chats")
                .child(userUid)
                .child("chat_list")
                .child(myUid)
                .child("favourite")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            uFavourite = dataSnapshot.getValue(Integer.class);

                            dbRef.child("chats")
                                    .child(userUid)
                                    .child("chat_list")
                                    .child(myUid)
                                    .setValue(new ChatListUser(myData, -1 * new Date().getTime()
                                            , message, true, true, uFavourite));
                        }
                        else{
                            dbRef.child("chats")
                                    .child(userUid)
                                    .child("chat_list")
                                    .child(myUid)
                                    .setValue(new ChatListUser(myData, -1 * new Date().getTime()
                                            , message, true, true, 0));
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}

