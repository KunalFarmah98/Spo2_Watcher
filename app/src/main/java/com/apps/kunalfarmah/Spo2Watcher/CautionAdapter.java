package com.apps.kunalfarmah.Spo2Watcher;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.apps.kunalfarmah.Spo2Watcher.CautiousVitalSigns.BP;
import static com.apps.kunalfarmah.Spo2Watcher.CautiousVitalSigns.HIGH;
import static com.apps.kunalfarmah.Spo2Watcher.CautiousVitalSigns.HR;
import static com.apps.kunalfarmah.Spo2Watcher.CautiousVitalSigns.O2;
import static com.apps.kunalfarmah.Spo2Watcher.CautiousVitalSigns.RR;

public class CautionAdapter extends RecyclerView.Adapter<CautionAdapter.CautionHolder> {


    private ArrayList<CautiousVitalSigns> cautiousVitalSigns;

    public CautionAdapter(ArrayList<CautiousVitalSigns> cautiousVitalSigns) {
        this.cautiousVitalSigns = cautiousVitalSigns;
    }

    @NonNull
    @Override
    public CautionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater myInflater = LayoutInflater.from(viewGroup.getContext());
        final View myOwnView = myInflater.inflate(R.layout.caution_row, viewGroup, false);

        return new CautionHolder(myOwnView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CautionHolder cautionHolder, final int i) {

        switch(cautiousVitalSigns.get(i).getVitalSign()){
            case BP: cautionHolder.cautionIcon.setImageResource(R.drawable.bp_icon);
                    break;

            case RR: cautionHolder.cautionIcon.setImageResource(R.drawable.respiratory_rate_problem);
                    break;

            case O2: cautionHolder.cautionIcon.setImageResource(R.drawable.o2_icon);
                    break;

            case HR: cautionHolder.cautionIcon.setImageResource(R.drawable.heart_rate_problem);
                    break;
        }

        cautionHolder.cautionDescr.setText(cautiousVitalSigns.get(i).getDescription());
        cautionHolder.cautionSummary.setText(cautiousVitalSigns.get(i).getSummary());

        cautionHolder.remedyIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), RemedyActivity.class);
                switch(cautiousVitalSigns.get(i).getVitalSign()){
                    case BP: if(cautiousVitalSigns.get(i).getDescriptive() == HIGH){
                                    intent.putExtra("title", "Hypertension");
                        Log.i("MyLogs", cautiousVitalSigns.get(i).getDescription());
                            } else {
                                    intent.putExtra("title", "Low Blood Pressure");
                            }
                        break;

//                    case RR:

                    case O2: if(cautiousVitalSigns.get(i).getDescriptive()==HIGH) {
                        intent.putExtra("title", "Oxygen Saturation");
                    }
                    else
                        intent.putExtra("title","High Oxygen Levels");
                        break;

                    case HR: if(cautiousVitalSigns.get(i).getDescriptive() == HIGH){
                        intent.putExtra("title", "High Heart Rate");
                    } else {
                        intent.putExtra("title", "Low Heart Rate");
                    }
                        break;

                    case RR:if(cautiousVitalSigns.get(i).getDescriptive() == HIGH){
                        intent.putExtra("title", "Tachypnea");
                    } else {
                        intent.putExtra("title", "Bradypnea ");
                    }
                        break;

                }

                v.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return cautiousVitalSigns.size();
    }

    public class CautionHolder extends RecyclerView.ViewHolder{

        ImageView cautionIcon, remedyIcon;
        TextView cautionDescr, cautionSummary;

        public CautionHolder(@NonNull View itemView) {
            super(itemView);
            this.cautionIcon = itemView.findViewById(R.id.row_icon);
            this.cautionDescr = itemView.findViewById(R.id.row_descr);
            this.cautionSummary = itemView.findViewById(R.id.row_summary);
            this.remedyIcon = itemView.findViewById(R.id.row_remedy);
        }
    }
}
