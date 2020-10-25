package com.apps.kunalfarmah.Spo2Watcher;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutVitalSigns extends AppCompatActivity implements View.OnClickListener {

    TextView hyper,hypo,hhr,lhr,hoxy,loxy,hrsp,lrsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_vital_signs);

        hyper = findViewById(R.id.hyp);
        hypo = findViewById(R.id.hypo);

        hhr = findViewById(R.id.tal);
        lhr = findViewById(R.id.bal);

        loxy = findViewById(R.id.hypox);
        hoxy = findViewById(R.id.hyper);

        hrsp = findViewById(R.id.talch);
        lrsp = findViewById(R.id.brady);

        hyper.setOnClickListener(this);
        hypo.setOnClickListener(this);
        hhr.setOnClickListener(this);
        lhr.setOnClickListener(this);
        loxy.setOnClickListener(this);
        hoxy.setOnClickListener(this);
        hrsp.setOnClickListener(this);
        lrsp.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.hyp:
                Intent intent = new Intent(this,RemedyActivity.class);
                intent.putExtra("title","Hypertension");
                startActivity(intent);
                break;
            case R.id.hypo:
                Intent intent1 = new Intent(this,RemedyActivity.class);
                intent1.putExtra("title","Low Blood Presure");
                startActivity(intent1);
                break;
            case R.id.tal:
                Intent intent2 = new Intent(this,RemedyActivity.class);
                intent2.putExtra("title","High Heart Rate");
                startActivity(intent2);
                break;

            case R.id.bal:
                Intent intent3 = new Intent(this,RemedyActivity.class);
                intent3.putExtra("title","Low Heart Rate");
                startActivity(intent3);
                break;
            case R.id.hypox:
                Intent intent4 = new Intent(this,RemedyActivity.class);
                intent4.putExtra("title","Oxygen Saturation");
                startActivity(intent4);
                break;
            case R.id.hyper:
                Intent Info = new Intent(this, InfoActivity.class);
                Info.putExtra("title", "Hyperoxia");
                startActivity(Info);
                break;
            case R.id.talch:
                Intent Info2 = new Intent(this, InfoActivity.class);
                Info2.putExtra("title", "Tachypnea");
                startActivity(Info2);
                break;
            case R.id.brady:
                Intent Info1 = new Intent(this, InfoActivity.class);
                Info1.putExtra("title", "Bradypnea");
                startActivity(Info1);
                break;
        }

    }
}

