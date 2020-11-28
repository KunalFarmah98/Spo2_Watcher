package com.apps.kunalfarmah.Spo2Watcher;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
    SharedPreferences sPref,sPref1,sPref2;

    ArrayList<CautiousVitalSigns> caution = new ArrayList<>();
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital_signs_results);
        sPref = getSharedPreferences("MyVitals",MODE_PRIVATE);
        sPref1 = getSharedPreferences("MeasurementCount",MODE_PRIVATE);
        sPref2 = getSharedPreferences("measurement_type", MODE_PRIVATE);

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

        double spo2 = VO2, hr = VHR;
        sPref = getSharedPreferences("Info", MODE_PRIVATE);
        int maxHr = 220 - sPref.getInt("Age",21);
        int minHr = 30;
        int minO2 = 70;
        int maxO2 = 99;
        spo2 = (((spo2-minO2)/(maxO2-minO2))*2)-1;
        hr = (((hr-minHr)/(maxHr-minHr))*2)-1;
        String[] args = {String.valueOf(spo2),String.valueOf(hr)};
        DecisionTreeClassifier.main(args);
        Log.d("prediction",String.valueOf(DecisionTreeClassifier.estimation));
        // TODO:  put model here
        if(VO2<94){
            int count = sPref1.getInt("Count",0);
            ++count;
            if(count>=4) {
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
                if(sPref2.getBoolean("isPPG",true)){
                    dialog.findViewById(R.id.warning2).setVisibility(View.VISIBLE);
                }
                else{
                    dialog.findViewById(R.id.warning2).setVisibility(View.GONE);
                }
                dialog.show();
            }
            sPref1.edit().putInt("Count",count).apply();
        }
        else{
            sPref1.edit().putInt("Count",0).apply();
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