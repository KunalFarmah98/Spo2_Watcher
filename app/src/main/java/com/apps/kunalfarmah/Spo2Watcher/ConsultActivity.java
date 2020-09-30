package com.apps.kunalfarmah.Spo2Watcher;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ConsultActivity extends AppCompatActivity {

    RecyclerView doctorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (ContextCompat.checkSelfPermission(ConsultActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ConsultActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }



        doctorList = findViewById(R.id.doctor_list);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("userbase")
                .child("doctors")
                .child("details")
                .limitToFirst(50);

        DoctorListAdapter doctorListAdapter = new DoctorListAdapter(R.layout.doctor_info_layout, query, getApplicationContext());
        doctorList.setAdapter(doctorListAdapter);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        doctorList.setLayoutManager(layoutManager);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
