package com.apps.kunalfarmah.Spo2Watcher;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class tempActivity extends AppCompatActivity implements View.OnClickListener {

    Button b1,b2,b3,b4,b5,b6,b7,b8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        b1= findViewById(R.id.hyp);
        b1.setOnClickListener(this);

        b2 = findViewById(R.id.hop);
        b2.setOnClickListener(this);

        b3 = findViewById(R.id.hr);
        b3.setOnClickListener(this);

        b4 = findViewById(R.id.oxy);
        b4.setOnClickListener(this);

        b5 = findViewById(R.id.hoxy);
        b5.setOnClickListener(this);

        b6 = findViewById(R.id.tal);
        b6.setOnClickListener(this);

        b7 = findViewById(R.id.lbr);
        b7.setOnClickListener(this);

        b8 = findViewById(R.id.lr);
        b8.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.hyp :
                Intent hyp = new Intent(this,RemedyActivity.class);
                hyp.putExtra("title","Hypertension");
                startActivity(hyp);
                break;


            case R.id.hop :
                Intent hop = new Intent(this,RemedyActivity.class);
                hop.putExtra("title","Low Blood Presure");
                startActivity(hop);
                break;

            case R.id.hr :
                Intent hr = new Intent(this,RemedyActivity.class);
                hr.putExtra("title","High Heart Rate");
                startActivity(hr);
                break;

            case R.id.oxy:
                Intent oxy = new Intent(this,RemedyActivity.class);
                oxy.putExtra("title","Oxygen Saturation");
                startActivity(oxy);
                break;

            case R.id.tal:
                Intent Info2 = new Intent(this, InfoActivity.class);
                Info2.putExtra("title", "Tachypnea");
                startActivity(Info2);
                break;

            case R.id.lbr:
                Intent Info1 = new Intent(this, InfoActivity.class);
                Info1.putExtra("title", "Bradypnea");
                startActivity(Info1);
                break;

            case R.id.lr:
                Intent lr = new Intent(this,RemedyActivity.class);
                lr.putExtra("title","Low Heart Rate");
                startActivity(lr);
                break;

            case R.id.hoxy:

                Intent Info = new Intent(this, InfoActivity.class);
                Info.putExtra("title", "Hyperoxia");
                startActivity(Info);
                break;


        }
    }
}
