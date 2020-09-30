package com.apps.kunalfarmah.Spo2Watcher;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class VitalSignsResults extends AppCompatActivity {

    private String user, Date;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    java.util.Date today = Calendar.getInstance().getTime();
    int VBP1, VBP2, VRR, VHR, VO2;
    SharedPreferences sPref;

    ArrayList<CautiousVitalSigns> caution = new ArrayList<>();
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital_signs_results);
        sPref = getSharedPreferences("MyVitals",MODE_PRIVATE);
        Date = df.format(today);
        TextView VSHR = this.findViewById(R.id.HRV);
        TextView VSO2 = this.findViewById(R.id.O2V);

        recyclerView = this.findViewById(R.id.caution_signs);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            VRR = bundle.getInt("breath");
            VHR = bundle.getInt("bpm");
            VO2 = bundle.getInt("O2R");

            SharedPreferences.Editor editor = sPref.edit();
            editor.clear();
            editor.apply();
            editor.putString("HR", String.valueOf(VHR));
            editor.putString("OS", String.valueOf(VO2));
            editor.apply();

            Log.i("MyLogs", Objects.requireNonNull(FirebaseAuth.getInstance().getUid()) );

            FirebaseDatabase.getInstance().getReference()
            .child("userbase")
            .child("patients")
            .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
            .child("vitals")
            .push()
            .setValue(new PatientSigns(VHR, VO2));

            user = bundle.getString("Usr");
            VSHR.setText(String.valueOf(VHR));
            VSO2.setText(String.valueOf(VO2));
        }

        if (VHR < 60) {
            caution.add(new CautiousVitalSigns(CautiousVitalSigns.LOW, CautiousVitalSigns.HR));
        } else if (VHR > 90) {
            caution.add(new CautiousVitalSigns(CautiousVitalSigns.HIGH, CautiousVitalSigns.HR));
        }

        if (VO2 < 95) {
            caution.add(new CautiousVitalSigns(CautiousVitalSigns.LOW, CautiousVitalSigns.O2));
        } else if (VO2 > 100) {
            caution.add(new CautiousVitalSigns(CautiousVitalSigns.HIGH, CautiousVitalSigns.O2));
        }

        // TODO:  put model here
        if(VO2<=92){
            AlertDialog.Builder builder = new AlertDialog.Builder(VitalSignsResults.this);
            builder.setView(R.layout.custom_spo2_alert_dialog);
            builder.setNegativeButton("Test Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    startActivity(new Intent(VitalSignsResults.this, RecordVitalSigns.class));
                }
            });

            builder.setPositiveButton("Seek Help", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    startActivity(new Intent(VitalSignsResults.this, HelpActivity.class));
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }

        CautionAdapter adapter = new CautionAdapter(caution);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

    }

    @Override
    public void onBackPressed() {
        // startActivity(new Intent(VitalSignsResults.this,MainActivity.class));
        finish();
    }


}