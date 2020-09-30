package com.apps.kunalfarmah.Spo2Watcher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class PatientsFragment extends Fragment {

    private RecyclerView patientList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patients,container,false);


        patientList = view.findViewById(R.id.doctor_patients_list);

        //TODO: Add recent vitals to the list

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("userbase")
                .child("doctors")
                .child(FirebaseAuth.getInstance().getUid())
                .child("patients");

        AppointmentListAdapter appointmentListAdapter = new AppointmentListAdapter(R.layout.appointment_layout, query, getContext(), true);
        patientList.setAdapter(appointmentListAdapter);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        patientList.setLayoutManager(layoutManager);


        return view;
    }
}
