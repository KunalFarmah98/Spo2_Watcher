package com.apps.kunalfarmah.Spo2Watcher.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.apps.kunalfarmah.Spo2Watcher.model.Info;
import com.apps.kunalfarmah.Spo2Watcher.R;

import java.util.ArrayList;

public class InfoAdapter extends ArrayAdapter<Info> {
    public InfoAdapter(Activity context, ArrayList<Info> info){
        super(context,0,info);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_info, parent, false);
        }

        final String title = getItem(position).getTopic();
        final String body = getItem(position).getInfo();


        TextView tv = listItemView.findViewById(R.id.topic);
        if(title.equalsIgnoreCase(""))
            tv.setVisibility(View.GONE);
        else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(title);
        }
        TextView info = listItemView.findViewById(R.id.info);
        info.setText(body);

        return listItemView;

    }
}
