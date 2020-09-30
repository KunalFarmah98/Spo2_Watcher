package com.apps.kunalfarmah.Spo2Watcher;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;


public class DoctorListAdapter extends FirebaseRecyclerAdapter<DoctorDetails, DoctorListAdapter.DoctorHolder>{

    Context context;
    /**
     * @param modelLayout     This is the layout used to represent a single item in the list.
     *                        You will be responsible for populating an instance of the corresponding
     *                        view with the data from an instance of modelClass.
     * @param ref             The Firebase location to watch for data changes. Can also be a slice of a location,
     *                        using some combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
     */
    public DoctorListAdapter(int modelLayout, Query ref, Context context) {
        super(DoctorDetails.class, modelLayout, DoctorHolder.class, ref);
        this.context = context;
    }

    @NonNull
    @Override
    public DoctorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_info_layout, parent, false);
        return new DoctorHolder(view);
    }


    @Override
    protected void populateViewHolder(DoctorHolder viewHolder, final DoctorDetails model, int position) {
        viewHolder.doctorName.setText(model.getName());
        viewHolder.doctorQualification.setText(model.getQualification());
        viewHolder.doctorHospital.setText(model.getHospital());
        viewHolder.doctorAddress.setText(model.getAddress());
        viewHolder.doctorSpeciality.setText("Speciality: " + model.getSpecialities());
        viewHolder.overflowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.doctor_click_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_start_chat:
                                final Intent intent = new Intent(context, ChatRoomActivity.class);
                                intent.putExtra("user_uid", model.getDoctorID());
                                intent.putExtra("user_name", model.getName());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                                return true;
                            case R.id.action_appointment:
                                Intent i = new Intent(v.getContext(), TakeAppointmentForm.class);
                                i.putExtra("DoctorName", model.getName());
                                i.putExtra("DoctorID", model.getDoctorID());
                                i.putExtra("Fees", model.getAppointmentCharge());

                                v.getContext().startActivity(i);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        });

        viewHolder.doctorLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Location", Toast.LENGTH_SHORT).show();
            }
        });


    }



    public class DoctorHolder extends RecyclerView.ViewHolder {
        TextView doctorName, doctorQualification, doctorSpeciality, doctorAddress, doctorHospital;
        ImageView doctorLocation, overflowButton;

        public DoctorHolder(@NonNull View itemView) {
            super(itemView);
            this.doctorName = itemView.findViewById(R.id.doctorName);
            this.doctorQualification = itemView.findViewById(R.id.doctor_qual);
            this.doctorSpeciality = itemView.findViewById(R.id.doctor_speciality);;
            this.doctorHospital = itemView.findViewById(R.id.doctor_hospital);
            this.doctorAddress = itemView.findViewById(R.id.doctor_address);;
            this.doctorLocation = itemView.findViewById(R.id.doctor_location);;
            this.overflowButton = itemView.findViewById(R.id.overflow_icon);;
        }
    }
}
