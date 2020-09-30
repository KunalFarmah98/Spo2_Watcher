package com.apps.kunalfarmah.Spo2Watcher;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultsViewHolder> {

    private ArrayList<PatientSigns> items;
    private Context mContext;

    public ResultsAdapter(Context applicationContext, ArrayList<PatientSigns> list) {
        mContext=applicationContext;
        items= list;
    }

    @NonNull
    @Override
    public ResultsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_result, viewGroup, false);
        return new ResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsViewHolder holder, int i) {
        PatientSigns item = items.get(i);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy    HH:mm:ss");
        String date = df.format(item.getTimestamp());
        SpannableString s = new SpannableString(date);
        s.setSpan(new UnderlineSpan(), 0, s.length(), 0);
        holder.time.setText(s);
        holder.hr.setText("Heart Rate: " +item.getHeartRate());
        holder.o2.setText("SPO2: " + item.getOxygenSaturation());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ResultsViewHolder extends RecyclerView.ViewHolder {
        TextView time,hr,o2;
        public ResultsViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            hr = itemView.findViewById(R.id.hr);
            o2 = itemView.findViewById(R.id.spo2);
        }
    }
}
