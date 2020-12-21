package com.apps.kunalfarmah.Spo2Watcher.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apps.kunalfarmah.Spo2Watcher.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ArduinoActivity extends AppCompatActivity {

    private LinearLayout data, waiting;
    private DatabaseReference oxymeterRef, hrRef, spo2Ref;
    private TextView hr, spo2;
    private CountDownTimer timer;
    private ValueEventListener hrListener, spo2Listener;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arduino);
        data = findViewById(R.id.data);
        waiting = findViewById(R.id.waiting);
        hr = findViewById(R.id.hr);
        spo2 = findViewById(R.id.spo2);
        id = getIntent().getStringExtra("id");
        timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent i = new Intent(ArduinoActivity.this, VitalSignsResults.class);
                i.putExtra("O2R", spo2.getText().toString());
                i.putExtra("bpm", hr.getText().toString());
                i.putExtra("Usr", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                startActivity(i);
                finish();
            }
        };
        oxymeterRef = FirebaseDatabase.getInstance().getReference(id).child("pulse oxymeter");
        hrRef = oxymeterRef.child("hr");
        hrListener  = (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String val = dataSnapshot.getValue(String.class);
                hr.setText(val);
                if(null!= val  && !val.equals("0") && !val.equals("")) {
                    timer.start();
                    waiting.setVisibility(View.GONE);
                    data.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        spo2Ref = oxymeterRef.child("spo2");
        spo2Listener = (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String val = dataSnapshot.getValue(String.class);
                spo2.setText(val);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        hrRef.addValueEventListener(hrListener);
        spo2Ref.addValueEventListener(spo2Listener);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        hrRef.removeEventListener(hrListener);
        spo2Ref.removeEventListener(spo2Listener);
    }
}