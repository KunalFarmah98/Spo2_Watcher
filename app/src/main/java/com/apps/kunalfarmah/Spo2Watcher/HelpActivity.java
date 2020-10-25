package com.apps.kunalfarmah.Spo2Watcher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends AppCompatActivity {

    WebView webView;
    TextView helpline,helpline1,website,locator;
    LinearLayout mainLayout;
    RecyclerView recycler;
    HelplineAdapter mAdapter;
    List<RegionalItem>numbers;
    SharedPreferences sPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getSupportActionBar().setTitle("Help");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        helpline = findViewById(R.id.helpline);
        helpline1 = findViewById(R.id.helpline2);
        website = findViewById(R.id.website);
        locator = findViewById(R.id.center_locator);
        webView = findViewById(R.id.web);
        mainLayout = findViewById(R.id.main_layout);
        numbers = new ArrayList<>();
        recycler = findViewById(R.id.helplines);
        sPref = getSharedPreferences("Data",MODE_PRIVATE);
        Type type = new TypeToken<List<RegionalItem>>() {}.getType();
        numbers = new Gson().fromJson(sPref.getString("Helpline",""), type);
        if(numbers==null){
            numbers=new ArrayList<>();
        }
        mAdapter = new HelplineAdapter(this,numbers);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(mAdapter);
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainLayout.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                webView.loadUrl("https://www.mohfw.gov.in/");
            }
        });


        helpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:"+helpline.getText().toString()));
                startActivity(call);
            }
        });

        helpline1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:"+helpline1.getText().toString()));
                startActivity(call);
            }
        });

        locator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }


    @Override
    public void onBackPressed() {
        if(webView.getVisibility()==View.VISIBLE){
            webView.setVisibility(View.GONE);
            mainLayout.setVisibility(View.VISIBLE);
        }
        else
            super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home :
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}