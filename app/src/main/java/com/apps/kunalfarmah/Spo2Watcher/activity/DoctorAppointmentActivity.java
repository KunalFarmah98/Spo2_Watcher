package com.apps.kunalfarmah.Spo2Watcher.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;

import com.apps.kunalfarmah.Spo2Watcher.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class DoctorAppointmentActivity extends AppCompatActivity {

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.appointment_new:
                        mTextMessage.setText("New Appointment");
                        return true;
                    case R.id.appointment_confirmed:
                        mTextMessage.setText("Confirmed Appointment");
                        return true;
                }
                return false;
            }
        });
    }

}
