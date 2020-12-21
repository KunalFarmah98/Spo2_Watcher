package com.apps.kunalfarmah.Spo2Watcher.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.kunalfarmah.Spo2Watcher.R;
import com.apps.kunalfarmah.Spo2Watcher.adapter.AppointmentListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DoctorAppointmentFragment extends Fragment {

    private RecyclerView appointmentList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doc_appointments,container,false);


        appointmentList = view.findViewById(R.id.doctor_appointments_list);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("userbase")
                .child("doctors")
                .child(FirebaseAuth.getInstance().getUid())
                .child("appointments")
                .limitToFirst(50);

        AppointmentListAdapter appointmentListAdapter = new AppointmentListAdapter(R.layout.appointment_layout, query, getContext());
        appointmentList.setAdapter(appointmentListAdapter);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        appointmentList.setLayoutManager(layoutManager);


        return view;
    }
}
