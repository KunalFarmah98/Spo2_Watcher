package com.apps.kunalfarmah.Spo2Watcher;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AppointmentListAdapter extends FirebaseRecyclerAdapter<PatientAppointment, AppointmentListAdapter.AppointmentHolder> {

    Context context;
    boolean patients;

    public AppointmentListAdapter(int modelLayout, Query ref, Context context) {
        super(PatientAppointment.class, modelLayout, AppointmentHolder.class, ref);
        this.context = context;
        this.patients = false;

    }

    public AppointmentListAdapter(int modelLayout, Query ref, Context context, boolean patients) {
        super(PatientAppointment.class, modelLayout, AppointmentHolder.class, ref);
        this.context = context;
        this.patients = patients;
    }

    @NonNull
    @Override
    public AppointmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_layout, parent, false);
        return new AppointmentHolder(view);
    }


    @Override
    protected void populateViewHolder(final AppointmentHolder viewHolder, final PatientAppointment model, final int position) {
        if (context.getSharedPreferences("doctor_logo", Context.MODE_PRIVATE).getBoolean("isDoctor", false)) {
            viewHolder.appName.setText(model.getPatientName());
            if (patients) {
                viewHolder.appDate.setVisibility(View.GONE);
                viewHolder.appTime.setVisibility(View.GONE);
                viewHolder.appDescription.setVisibility(View.GONE);
                viewHolder.status.setVisibility(View.VISIBLE);
                viewHolder.clock.setVisibility(View.GONE);
                viewHolder.cal.setVisibility(View.GONE);
                if (model.getActive() == 1) {
                    viewHolder.status.setText("ACTIVE");
                } else if(model.getActive()==0){
                    viewHolder.status.setText("DISCHARGED");
                }
                DatabaseReference query = FirebaseDatabase.getInstance()
                        .getReference()
                        .child("userbase")
                        .child("patients")
                        .child(model.getPatientID())
                        .child("vitals").limitToLast(1).getRef();

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        PatientSigns signs = null;
                        for (DataSnapshot vitals : dataSnapshot.getChildren()) {
                            signs = vitals.getValue(PatientSigns.class);
                            Log.e("Vitals:", String.valueOf(signs.getHeartRate()));
                        }
                        viewHolder.hr.setText(String.valueOf(signs.getHeartRate()));
                        viewHolder.os.setText(String.valueOf(signs.getOxygenSaturation()));

                        String[] args = {String.valueOf(signs.getHeartRate()),String.valueOf(signs.getOxygenSaturation())};
                        DecisionTreeClassifier.main(args);
                        Log.d("prediction",String.valueOf(DecisionTreeClassifier.estimation));
                        //TODO: change background if signs are critical

                        if(DecisionTreeClassifier.estimation==1 || signs.getOxygenSaturation()<=90){
                            viewHolder.mainLayout.setBackgroundColor(context.getResources().getColor(R.color.red));
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } else {
                viewHolder.appDate.setVisibility(View.VISIBLE);
                viewHolder.appTime.setVisibility(View.VISIBLE);
                viewHolder.appDescription.setVisibility(View.VISIBLE);
                viewHolder.clock.setVisibility(View.VISIBLE);
                viewHolder.cal.setVisibility(View.VISIBLE);
                viewHolder.status.setVisibility(View.GONE);
                viewHolder.appDate.setText(model.getDate());
                viewHolder.appTime.setText(model.getTime());
                viewHolder.appDescription.setText("Symptoms: " + model.getDescription());
                Vitals v = model.getVitals();
                //viewHolder.bp.setText(v.getBp());
                viewHolder.hr.setText(v.getHr());
                viewHolder.os.setText(v.getOs());
                //viewHolder.rr.setText(v.getRr());
            }
            viewHolder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setCancelable(true);
                    if (!patients) {
                        dialog.setTitle("Appointment with "+model.getPatientName()+" @ "+model.getTime()+" on "+model.getDate());
                        dialog.setIcon(R.drawable.baseline_assignment_24);
                        dialog.setPositiveButton("Contact", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Intent call = new Intent(Intent.ACTION_DIAL);
                                call.setData(Uri.parse("tel:"+model.getMobile()));
                                context.startActivity(call);
                            }
                        });
                        dialog.setNegativeButton("Mark as Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                model.setDone(true);
                                getRef(position).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Appointment Marked as Done", Toast.LENGTH_SHORT).show();
                                        notifyDataSetChanged();
                                        dialog.cancel();
                                    }
                                });
                            }
                        });
                        AlertDialog d = dialog.create();
                        d.show();
                    } else {
                        dialog.setTitle(model.getPatientName()+" : Select an action");
                        dialog.setIcon(R.drawable.ic_person_black_24dp);
                        dialog.setNeutralButton("Contact", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Intent call = new Intent(Intent.ACTION_DIAL);
                                call.setData(Uri.parse("tel:"+model.getMobile()));
                                context.startActivity(call);
                            }
                        });
                        dialog.setPositiveButton("Discharge", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                model.setActive(0);
                                getRef(position).setValue(model);
                                notifyDataSetChanged();
                                dialog.cancel();
                            }
                        });
                        dialog.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                model.setDone(true);
                                getRef(position).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Patient Removed Successfully", Toast.LENGTH_SHORT).show();
                                        notifyDataSetChanged();
                                        dialog.cancel();
                                    }
                                });
                            }
                        });
                        AlertDialog d = dialog.create();
                        d.show();
                    }
                }
            });
        } else {
            viewHolder.appName.setText("Doctor Name: " + model.getDoctorName());
            viewHolder.appDescription.setText(model.getDescription());
            viewHolder.vitals.setVisibility(View.GONE);
            viewHolder.status.setVisibility(View.GONE);
            viewHolder.appDate.setText(model.getDate());
            viewHolder.appTime.setText(model.getTime());
            viewHolder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("Appointment with Dr."+model.getDoctorName()+"@"+model.getTime()+"on "+model.getDate());
                    dialog.setIcon(R.drawable.baseline_assignment_24);
                    dialog.setCancelable(true);
                    dialog.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            getRef(position).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(context, "Appointment Removed", Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();
                                }
                            });
                        }
                    });
                    dialog.setPositiveButton("Mark as Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            model.setDone(true);
                            getRef(position).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(context, "Appointment Marked as Done", Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();
                                    dialog.cancel();
                                }
                            });
                        }
                    });
                    AlertDialog d = dialog.create();
                    d.show();
                }
            });
        }

    }


    public class AppointmentHolder extends RecyclerView.ViewHolder {
        TextView appName, appDescription, appTime, appDate, status;
        TextView bp, hr, os, rr;
        ImageView clock,cal;
        LinearLayout vitals;
        CardView mainLayout;

        public AppointmentHolder(@NonNull View itemView) {
            super(itemView);
            this.mainLayout = itemView.findViewById(R.id.main_layout);
            this.appName = itemView.findViewById(R.id.appointment_name);
            this.appDescription = itemView.findViewById(R.id.appointment_desc);
            this.appTime = itemView.findViewById(R.id.appointment_time);
            this.appDate = itemView.findViewById(R.id.appointment_date);
            this.vitals = itemView.findViewById(R.id.vitals);
            this.status = itemView.findViewById(R.id.status);
            this.bp = itemView.findViewById(R.id.bp);
            this.hr = itemView.findViewById(R.id.hr);
            this.os = itemView.findViewById(R.id.os);
            this.rr = itemView.findViewById(R.id.rr);
            this.cal = itemView.findViewById(R.id.date_icon);
            this.clock = itemView.findViewById(R.id.clock_icon);
        }

    }
}
