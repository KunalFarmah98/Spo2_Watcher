package com.apps.kunalfarmah.Spo2Watcher.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.kunalfarmah.Spo2Watcher.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ArduinoActivity extends AppCompatActivity {

    private LinearLayout data, waiting;
    private DatabaseReference oxymeterRef, hrRef, spo2Ref, vitals;
    private TextView hr, spo2;
    private CountDownTimer timer;
    private ValueEventListener hrListener, spo2Listener;
    private ChildEventListener vitalsListener;
    private String id;
    private boolean reading=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arduino);
        data = findViewById(R.id.data);
        waiting = findViewById(R.id.waiting);
        hr = findViewById(R.id.hr);
        spo2 = findViewById(R.id.spo2);
        id = getIntent().getStringExtra("id");
        oxymeterRef = FirebaseDatabase.getInstance().getReference().child("sensors").child(id).child("pulse_oximeter").child("vitals");
        vitals = oxymeterRef;
        //abnormal readings test case
       /*        Intent i = new Intent(RecordVitalSigns.this, VitalSignsResults.class);
            i.putExtra("O2R", 92);
            i.putExtra("bpm", 60);
            i.putExtra("Usr", user);
            startActivity(i);
            finish();*/
        timer = new CountDownTimer(25000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                if(!reading){
                    Toast.makeText(ArduinoActivity.this,"Finger not detected on the sensor. Please Try Again",Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                Intent i = new Intent(ArduinoActivity.this, VitalSignsResults.class);
                i.putExtra("O2R", Integer.parseInt(spo2.getText().toString()));
                i.putExtra("bpm", Integer.parseInt(hr.getText().toString()));
                i.putExtra("Usr", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                i.putExtra("updateDB",true);
                vitals.removeValue();
                startActivity(i);
                finish();
            }
        };
//        hrRef = oxymeterRef.child("hr");
        /*hrListener  = (new ValueEventListener() {
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
        spo2Ref.addValueEventListener(spo2Listener);*/

        vitals.addChildEventListener(vitalsListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                waiting.setVisibility(View.GONE);
                data.setVisibility(View.VISIBLE);
                String[] vals = Objects.requireNonNull(snapshot.getValue(String.class)).split(" ");
                if(!vals[0].equals("0") && !vals[1].equals("0")) {
                    reading=true;
                    hr.setText(vals[0].substring(0,vals[0].indexOf('.')));
                    spo2.setText(vals[1].substring(0,vals[1].indexOf('.')));
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        timer.start();



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
//        hrRef.removeEventListener(hrListener);
//        spo2Ref.removeEventListener(spo2Listener);
        vitals.removeEventListener(vitalsListener);
    }
}