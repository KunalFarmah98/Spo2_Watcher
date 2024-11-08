package com.apps.kunalfarmah.Spo2Watcher.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.apps.kunalfarmah.Spo2Watcher.R;

import mehdi.sakout.fancybuttons.FancyButton;

public class MonitoringInstructions extends AppCompatActivity {


    FancyButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_instructions);

        button = findViewById(R.id.start_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MonitoringInstructions.this, RecordVitalSigns.class));
                finish();
            }
        });
    }

//    @Override
//    public void onBackPressed() {
//        Intent i = new Intent(MonitoringInstructions.this, MainActivity.class);
//        startActivity(i);
//        finish();
//        super.onBackPressed();
//    }
}
