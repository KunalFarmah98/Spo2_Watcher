package com.apps.kunalfarmah.Spo2Watcher.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.kunalfarmah.Spo2Watcher.R;
import com.apps.kunalfarmah.Spo2Watcher.adapter.AppointmentListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class UserAppointments extends AppCompatActivity {

    RecyclerView appointmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_appointments);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appointmentList = findViewById(R.id.user_appointments_list);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("userbase")
                .child("patients")
                .child(FirebaseAuth.getInstance().getUid())
                .child("appointments")
                .limitToFirst(50);

        AppointmentListAdapter appointmentListAdapter = new AppointmentListAdapter(R.layout.appointment_layout, query, UserAppointments.this);
        appointmentList.setAdapter(appointmentListAdapter);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        appointmentList.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
