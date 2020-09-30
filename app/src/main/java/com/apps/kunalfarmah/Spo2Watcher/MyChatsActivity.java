package com.apps.kunalfarmah.Spo2Watcher;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MyChatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chats);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        displayUserList();

    }

//    public void startChat(View view) {
//        Intent intent = new Intent(getApplicationContext(), StartChatActivity.class);
//        startActivityForResult(intent, START_CHAT_REQUEST_CODE);
//    }
//
//    private void beginChat(String userUid, String userName) {
//        if (!userUid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
//            final Intent intent = new Intent(getApplicationContext(), ChatRoomActivity.class);
//            intent.putExtra("user_uid", userUid);
//            intent.putExtra("user_name", userName);
//            startActivity(intent);
//        } else {
//            Toast.makeText(this, "Cannot initiate chat with self.", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void displayUserList() {
        final RecyclerView listOfUsers = findViewById(R.id.list_of_users);

        Query query;
        if(getApplicationContext().getSharedPreferences
                ("settings", MODE_PRIVATE).getBoolean("VIEW_FAVOURITE", false)) {
            query = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("chats")
                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                    .child("chat_list")
                    .orderByChild("favourite")
                    .limitToLast(50);
        }

        else {
            query = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("chats")
                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                    .child("chat_list")
                    .orderByChild("lastMessageTime")
                    .limitToLast(50);
        }

        UserListAdapter adapter = new UserListAdapter(R.layout.user_layout, query, getApplicationContext());
        listOfUsers.setAdapter(adapter);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listOfUsers.setLayoutManager(layoutManager);
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
