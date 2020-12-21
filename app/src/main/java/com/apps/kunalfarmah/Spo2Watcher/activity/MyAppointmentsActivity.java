package com.apps.kunalfarmah.Spo2Watcher.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.apps.kunalfarmah.Spo2Watcher.R;

public class MyAppointmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_my_appointments);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
