package com.apps.kunalfarmah.Spo2Watcher.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.kunalfarmah.Spo2Watcher.R;
import com.apps.kunalfarmah.Spo2Watcher.model.Remedy;

import java.util.ArrayList;

public class RemedyAdapter extends ArrayAdapter<Remedy>{

    public RemedyAdapter(Activity context, ArrayList<Remedy> remedies){
        super(context,0,remedies);
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        final Remedy remedy =getItem(position);
        String data = remedy.getRemedies();
        int imgId = remedy.getImages();


        TextView title = listItemView.findViewById(R.id.remedy);
        title.setText(remedy.getRemedy_title());
        TextView _data = listItemView.findViewById(R.id.data);
        _data.setText(data);
        ImageView img = listItemView.findViewById(R.id.img);
        if(imgId>0){
            img.setVisibility(View.VISIBLE);
            img.setImageResource(imgId);
        }

        else
            img.setVisibility(View.GONE);

        return listItemView;
    }
}
