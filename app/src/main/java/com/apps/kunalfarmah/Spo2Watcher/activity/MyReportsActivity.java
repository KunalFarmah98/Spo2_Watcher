package com.apps.kunalfarmah.Spo2Watcher.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.apps.kunalfarmah.Spo2Watcher.model.PatientSigns;
import com.apps.kunalfarmah.Spo2Watcher.R;
import com.apps.kunalfarmah.Spo2Watcher.adapter.ResultsAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;

public class MyReportsActivity extends AppCompatActivity {

    private static final String TAG = "DATA";
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
                        return Long.compare(o2.getTimestamp(),o1.getTimestamp());
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
            case R.id.save:
                saveData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.results_menu, menu);
        return true;
    }


    void saveData() {
        if(!isStoragePermissionGranted())return;

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return;
            }
        }
        File file;
        String mImageName="Results.txt";
        file = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        try {
            FileWriter writer = new FileWriter(file);
            writer.append("Heart Rate \t\t SPO2\n");
            for (PatientSigns data : list) {
                writer.append(data.getHeartRate() + " \t\t\t " + data.getOxygenSaturation() + "\n");
            }
            writer.flush();
            writer.close();
            Toast.makeText(this,"Saved in"+file.getAbsolutePath(),Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
        }
    }


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted1");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted1");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                Log.d(TAG, "External storage2");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    // continue work that had asked for permission
                    saveData();
                }else{
                    Toast.makeText(MyReportsActivity.this,"Please Provide the Permission to Continue",Toast.LENGTH_SHORT).show();
                }
                break;

            case 3:
                Log.d(TAG, "External storage1");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                }else{
                    Toast.makeText(MyReportsActivity.this,"Please Provide the Permission to Continue",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}