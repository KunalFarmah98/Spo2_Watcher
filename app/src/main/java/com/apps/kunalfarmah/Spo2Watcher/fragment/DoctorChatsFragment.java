package com.apps.kunalfarmah.Spo2Watcher.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.kunalfarmah.Spo2Watcher.R;
import com.apps.kunalfarmah.Spo2Watcher.adapter.UserListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class DoctorChatsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doc_chats,container,false);
        RecyclerView listOfUsers = view.findViewById(R.id.list_of_users);

        Query query;
        if(getContext().getSharedPreferences
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

        UserListAdapter adapter = new UserListAdapter(R.layout.user_layout, query, getContext());
        listOfUsers.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        listOfUsers.setLayoutManager(layoutManager);
        return view;
    }


    private void displayUserList() {

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
}
