package com.apps.kunalfarmah.Spo2Watcher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class HelplineAdapter extends RecyclerView.Adapter<HelplineAdapter.HelplineVH> {
    Context mContext;
    List<RegionalItem> items;

    public HelplineAdapter(Context context, List<RegionalItem> list){
        mContext=context;
        items = list;
    }

    @NonNull
    @Override
    public HelplineVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater myInflater = LayoutInflater.from(viewGroup.getContext());
        final View myOwnView = myInflater.inflate(R.layout.helpline_list_item, viewGroup, false);

        return new HelplineVH(myOwnView);
    }

    @Override
    public void onBindViewHolder(@NonNull final HelplineVH helplineVH, final int i) {
        final RegionalItem item = items.get(i);
        helplineVH.loc.setText(item.getLoc().toString());
        String number = item.getNumber();
        String [] list = number.split(",");
        SpannableString content = new SpannableString(list[0]);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        helplineVH.number.setText(content);
        helplineVH.number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:"+helplineVH.number.getText().toString()));
                mContext.startActivity(call);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class HelplineVH extends RecyclerView.ViewHolder {
        TextView loc,number;
        public HelplineVH(@NonNull View itemView) {
            super(itemView);
            loc = itemView.findViewById(R.id.loc);
            number = itemView.findViewById(R.id.number);
        }
    }
}
