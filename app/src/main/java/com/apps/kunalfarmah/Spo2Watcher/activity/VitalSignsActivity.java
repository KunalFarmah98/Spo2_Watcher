package com.apps.kunalfarmah.Spo2Watcher.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.apps.kunalfarmah.Spo2Watcher.R;

import mehdi.sakout.fancybuttons.FancyButton;

public class VitalSignsActivity extends AppCompatActivity {

    FancyButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital_signs);
        button = findViewById(R.id.monitor_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VitalSignsActivity.this, MonitoringInstructions.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
