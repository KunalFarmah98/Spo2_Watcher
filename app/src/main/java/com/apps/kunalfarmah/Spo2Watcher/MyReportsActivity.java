package com.apps.kunalfarmah.Spo2Watcher;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;

public class MyReportsActivity extends AppCompatActivity {

    RecyclerView resultsRecycler;
    ResultsAdapter mAdapter;
    ArrayList<PatientSigns> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reports);
        getSupportActionBar().setTitle("My Vitals Results");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list = new ArrayList<>();
        resultsRecycler = findViewById(R.id.results_recycler);

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference()
                                            .child("userbase")
                                            .child("patients")
                                            .child(FirebaseAuth.getInstance().getUid())
                                            .child("vitals");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot vitals : dataSnapshot.getChildren()) {
                    PatientSigns signs = vitals.getValue(PatientSigns.class);
                    list.add(signs);
                    Log.e("Vitals:", String.valueOf(signs.getHeartRate()));

                }
                list.sort(new Comparator<PatientSigns>() {
                    @Override
                    public int compare(PatientSigns o1, PatientSigns o2) {
                        return (int) (o2.getTimestamp() - o1.getTimestamp());
                    }
                });
                mAdapter = new ResultsAdapter(getApplicationContext(), list);
                resultsRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                resultsRecycler.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onNavigateUp() {
        return super.onNavigateUp();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
        case R.id.home:
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}