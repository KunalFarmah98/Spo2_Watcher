package com.apps.kunalfarmah.Spo2Watcher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class DoctorActivity extends AppCompatActivity {

    SharedPreferences sharedPref,sref;
    Boolean doctorDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPref= getSharedPreferences("doctor_logo",Context.MODE_PRIVATE);
        doctorDetails = sharedPref.getBoolean("isDoctor",false);
        sref  = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        loadFragment(new DoctorAppointmentFragment());

        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.nav_view_main);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                int id = menuItem.getItemId();

                if (id == R.id.action_appointment) {
                    fragment = new DoctorAppointmentFragment();
                    loadFragment(fragment);
                    return true;
                } else if (id == R.id.action_chat) {
                    fragment=new DoctorChatsFragment();
                    loadFragment(fragment);
                    return true;
                }
                else if(id== R.id.action_my_patients){
                    fragment = new PatientsFragment();
                    loadFragment(fragment);
                    return true;
                }

                return true;
            }
        });
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_doctor_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if( id==R.id.action_logout){
            MainActivity.isDoctor=false;
            sharedPref.edit().clear().apply();
            sref.edit().clear().apply();
            AuthUI.getInstance()
                    .signOut(DoctorActivity.this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(DoctorActivity.this,MainActivity.class));
                        }
                    });
        }

        return super.onOptionsItemSelected(item);
    }

}
