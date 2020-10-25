package com.apps.kunalfarmah.Spo2Watcher;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TakeAppointmentForm extends AppCompatActivity {

    SharedPreferences sref;
    TextView name, age, gender, date, time;
    EditText problem, mob;
    DatePicker picker;
    TimePicker picker_time;
    TextView doc, fee;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_appointment_form);

        sref = getSharedPreferences("Info", MODE_PRIVATE);

        submit = findViewById(R.id.submit_appointment);

        name = findViewById(R.id.name);
        age = findViewById(R.id.Age);
        gender = findViewById(R.id.Gender);
        mob = findViewById(R.id.et_mob);

        final String Name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        final String Age = String.valueOf(sref.getInt("Age", 20));
        final String Gender = sref.getString("Gender", "M");


        name.setText(Name);

        age.setText("Age: " + Age);

        gender.setText("Gender: " + Gender);

        picker = findViewById(R.id.date_picker);
        picker_time = findViewById(R.id.time_picker);

        problem = findViewById(R.id.et_problem);

        time = findViewById(R.id.time);
        date = findViewById(R.id.date);

        doc = findViewById(R.id.doctor);
        fee = findViewById(R.id.fees);

        final String doctor = getIntent().getStringExtra("DoctorName");
        final String doctorID = getIntent().getStringExtra("DoctorID");
        String fees = String.valueOf(getIntent().getIntExtra("Fees", 100));

        doc.setText("Dr." + doctor);
        fee.setText("Fee: " + fees);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyVitals", 0);
        final String bp, hr, os, rr;
        //bp = pref.getString("BP","0/0");
        hr = pref.getString("HR", "0");
        os = pref.getString("OS", "0");
        //rr = pref.getString("RR","0");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String Problem = problem.getText().toString();
                final String Mob = mob.getText().toString();
                final Time appointmentTime = new Time(picker_time.getHour(), picker_time.getMinute(), picker.getDayOfMonth(),
                        picker.getMonth(), picker.getYear());
                // update database of appointments
                Vitals vitals = new Vitals(hr, os);
                final PatientAppointment patientAppointment = new PatientAppointment(Name, doctor, FirebaseAuth.getInstance().getUid(),

                        doctorID, appointmentTime, Gender, Integer.parseInt(Age), Problem, Mob, vitals);

                FirebaseDatabase.getInstance().getReference().child("userbase")
                        .child("doctors")
                        .child(doctorID)
                        .child("appointments")
                        .push()
                        .setValue(patientAppointment);

                final Patient patient = new Patient(Name,Age,Gender,FirebaseAuth.getInstance().getUid(),Mob,true);

                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("userbase")
                        .child("doctors")
                        .child(doctorID)
                        .child("patients");
                // checking if patient isn't present already
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean present = false;
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            try {
                                PatientAppointment pm = data.getValue(PatientAppointment.class);
                                if (pm.getPatientID().equalsIgnoreCase(FirebaseAuth.getInstance().getUid())) {
                                    present = true;
                                    break;
                                }
                            }
                            catch (Exception e){
                                break;
                            }

                        }
                        if (!present) {
                            ref.push();
                            ref.setValue(patientAppointment);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                FirebaseDatabase.getInstance().getReference().child("userbase")
                        .child("patients")
                        .child(FirebaseAuth.getInstance().getUid())
                        .child("appointments")
                        .push()
                        .setValue(patientAppointment);

                finish();
                startActivity(new Intent(TakeAppointmentForm.this, UserAppointments.class));


              /* final DatabaseReference rf = FirebaseDatabase.getInstance().getReference()
                       .child("userbase")
                       .child("patients");


               rf.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       Boolean is = dataSnapshot.hasChild(FirebaseAuth.getInstance().getUid());
                       if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getUid())) {
                           rf.child(FirebaseAuth.getInstance().getUid());
                           rf.child("vitals");
                           rf.addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                   String id = "";
                                   id = dataSnapshot.getKey();
                                   if (dataSnapshot.exists()) {
                                       for (DataSnapshot supportItem : dataSnapshot.getChildren()) {
                                           id = supportItem.getRef().getKey();
                                           break;
                                       }
                                       rf.child(id);
                                       rf.addListenerForSingleValueEvent(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                               String sys, dias, hr, os, rr;
                                               PatientSigns ps = dataSnapshot.getValue(PatientSigns.class);
                                               sys = String.valueOf(ps.getSystolic());
                                               dias = String.valueOf(ps.getDiastolic());
                                               hr = String.valueOf(ps.getHeartRate());
                                               os = String.valueOf(ps.getOxygenSaturation());
                                               rr = String.valueOf(ps.getRespirationRate());

                                               String bp = sys + "/" + dias;
                                               Vitals vitals = new Vitals(bp, hr, os, rr);
                                               PatientAppointment patientAppointment = new PatientAppointment(Name, doctor, FirebaseAuth.getInstance().getUid(),

                                                       doctorID, appointmentTime, Gender, Integer.parseInt(Age), Problem, vitals);

                                               FirebaseDatabase.getInstance().getReference().child("userbase")
                                                       .child("doctors")
                                                       .child(doctorID)
                                                       .child("appointments")
                                                       .push()
                                                       .setValue(patientAppointment);

                                               FirebaseDatabase.getInstance().getReference().child("userbase")
                                                       .child("patients")
                                                       .child(FirebaseAuth.getInstance().getUid())
                                                       .child("appointments")
                                                       .push()
                                                       .setValue(patientAppointment);

                                               finish();
                                               startActivity(new Intent(TakeAppointmentForm.this, UserAppointments.class));

                                           }

                                           @Override
                                           public void onCancelled(@NonNull DatabaseError databaseError) {

                                           }
                                       });
                                   } else {
                                       Vitals vitals = new Vitals("", "", "", "");
                                       PatientAppointment patientAppointment = new PatientAppointment(Name, doctor, FirebaseAuth.getInstance().getUid(),

                                               doctorID, appointmentTime, Gender, Integer.parseInt(Age), Problem, vitals);

                                       FirebaseDatabase.getInstance().getReference().child("userbase")
                                               .child("doctors")
                                               .child(doctorID)
                                               .child("appointments")
                                               .push()
                                               .setValue(patientAppointment);

                                       FirebaseDatabase.getInstance().getReference().child("userbase")
                                               .child("patients")
                                               .child(FirebaseAuth.getInstance().getUid())
                                               .child("appointments")
                                               .push()
                                               .setValue(patientAppointment);

                                       finish();
                                       startActivity(new Intent(TakeAppointmentForm.this, UserAppointments.class));

                                   }
                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError databaseError) {

                               }
                           });

                       } else {
                           Vitals vitals = new Vitals("", "", "", "");
                           PatientAppointment patientAppointment = new PatientAppointment(Name, doctor, FirebaseAuth.getInstance().getUid(),

                                   doctorID, appointmentTime, Gender, Integer.parseInt(Age), Problem, vitals);

                           FirebaseDatabase.getInstance().getReference().child("userbase")
                                   .child("doctors")
                                   .child(doctorID)
                                   .child("appointments")
                                   .push()
                                   .setValue(patientAppointment);

                           FirebaseDatabase.getInstance().getReference().child("userbase")
                                   .child("patients")
                                   .child(FirebaseAuth.getInstance().getUid())
                                   .child("appointments")
                                   .push()
                                   .setValue(patientAppointment);

                           finish();
                           startActivity(new Intent(TakeAppointmentForm.this, UserAppointments.class));
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });*/
                Log.i("MyLogsProb", Problem);

            }

        });

    }
}
