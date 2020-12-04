package com.apps.kunalfarmah.Spo2Watcher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;

import com.apps.kunalfarmah.Spo2Watcher.Android_wifi.ui.main.WifiActivity;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static String hr,os;

    int i = 0;

    static boolean isDoctor = false;
    ArrayList<PatientSigns> listSigns = new ArrayList<>();

    NavigationView navigationView;
    DrawerLayout mDrawerLayout;
    private DatabaseReference mDatabase;
    // shared preference for doctor_logo
    static SharedPreferences sharedPref,sref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // using shared preferrence to distinguish between doctor_logo and user
        sharedPref = getSharedPreferences("doctor_logo", Context.MODE_PRIVATE);
        sref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        isDoctor = sharedPref.getBoolean("isDoctor", true);

        if(isDoctor){
            startActivity(new Intent(this,DoctorActivity.class));
        }

        setContentView(R.layout.activity_main);
        final LineChart chart1 = findViewById(R.id.chart1);
        final LineChart chart2 = findViewById(R.id.chart2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");

        // using shared preferrence to distinguish between doctor_logo and user
        sharedPref = getSharedPreferences("doctor_logo", Context.MODE_PRIVATE);
        isDoctor = sharedPref.getBoolean("isDoctor", true);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else {

            if (isDoctor) {
                startActivity(new Intent(this, DoctorActivity.class));
            }


            final List<Entry> HRentries = new ArrayList<Entry>();
            final List<Entry> OSentries = new ArrayList<Entry>();




            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.nav_view);

            View header = navigationView.getHeaderView(0);
            TextView navText = header.findViewById(R.id.nav_text);
            TextView emailText = header.findViewById(R.id.nav_email);
            ImageView img = header.findViewById(R.id.img);
            if (user != null) {
                String name = user.getDisplayName();
                if(name==null)
                    name = "User";
                navText.setText("Hi! " + name);
                Picasso.get().load(user.getPhotoUrl()).into(img);
                emailText.setText(user.getEmail());

            }


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_vital_signs:
                        startActivity(new Intent(MainActivity.this, OptionsActivity.class));
                        return true;
                    case R.id.nav_consult:
                        startActivity(new Intent(MainActivity.this, ConsultActivity.class));
                        return true;
                    case R.id.nav_logout:
                        sharedPref.edit().clear().apply();
                        sref.edit().clear().apply();
                        AuthUI.getInstance()
                                .signOut(MainActivity.this)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    public void onComplete(@NonNull Task<Void> task) {
                                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                    }
                                });
                        return true;

                    case R.id.nav_my_results:
                        startActivity(new Intent(MainActivity.this,MyReportsActivity.class));
                        return true;

                    case R.id.nav_my_appoints:
                        startActivity(new Intent(MainActivity.this, UserAppointments.class));
                        return true;

                    case R.id.nav_my_chats:
                        startActivity(new Intent(MainActivity.this, MyChatsActivity.class));
                        return true;

                    case R.id.nav_help:
                        startActivity(new Intent(MainActivity.this,HelpActivity.class));
                        return true;

                    case R.id.nav_abt_vitals:
                        startActivity(new Intent(MainActivity.this, AboutVitalSigns.class));
                        return true;

                    case R.id.nav_settings:
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        return true;

                }

                return false;
            }});

            mDatabase = FirebaseDatabase.getInstance().getReference()
                    .child("userbase")
                    .child("patients")
                    .child(FirebaseAuth.getInstance().getUid())
                    .child("vitals");

            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot vitals : dataSnapshot.getChildren()) {
                        Log.d("data", "onDataChange: " + vitals.toString());
                        PatientSigns signs = vitals.getValue(PatientSigns.class);
                        listSigns.add(signs);

                        hr = String.valueOf(listSigns.get(0).getHeartRate());
                        os = String.valueOf(listSigns.get(0).getOxygenSaturation());


                        Log.i("retrieved: ", Integer.toString(listSigns.get(i).getHeartRate()));
                        HRentries.add(new Entry(1 + i, listSigns.get(i).getHeartRate()));
                        OSentries.add(new Entry(1 + i, listSigns.get(i).getOxygenSaturation()));
                        //Log.d("class", "onDataChange: "+signs.getDiastolic());
                        //Log.d("entries retrieved", "onDataChange: "+HRentries.get(i).getY());
                        i = i + 1;

                        LineDataSet dataSet1 = new LineDataSet(HRentries, "Beats per minute"); // add entries to dataset
                        dataSet1.setColor(R.color.maroon);
                        //dataSet1.getLabel().
                        chart1.setBackgroundColor(getResources().getColor(R.color.myMessageColor));
                        Description description1 = new Description();
                        description1.setText("Heart Rate");
                        chart1.setDescription(description1);
                        LineData lineData1 = new LineData(dataSet1);
                        chart1.setData(lineData1);
                        Legend legend1 = chart1.getLegend();
                        //legend1.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
                        legend1.setTypeface(Typeface.DEFAULT_BOLD);
                        legend1.setTextSize(17f);
                        chart1.invalidate(); // refresh
                        chart1.notifyDataSetChanged();

                        LineDataSet dataSet2 = new LineDataSet(OSentries, "Percentage"); // add entries to dataset
                        dataSet2.setColor(R.color.maroon);
                        chart2.setBackgroundColor(getResources().getColor(R.color.pink));
                        Description description4 = new Description();
                        description4.setText("Oxygen Saturation");
                        chart2.setDescription(description4);
                        LineData lineData2 = new LineData(dataSet2);
                        chart2.setData(lineData2);
                        Legend legend4 = chart2.getLegend();
                        legend4.setTypeface(Typeface.DEFAULT_BOLD);
                        legend4.setTextSize(17f);
                        chart2.invalidate(); // refresh
                        chart2.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        else if(id==R.id.action_settings){
            startActivity(new Intent(MainActivity.this,SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


    int scaleBetween(int unscaledNum, int minAllowed, int maxAllowed, int min, int max) {
        return (maxAllowed - minAllowed) * (unscaledNum - min) / (max - min) + minAllowed;
    }
}
