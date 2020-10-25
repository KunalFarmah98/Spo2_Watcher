package com.apps.kunalfarmah.Spo2Watcher;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PatientInfoActivity extends AppCompatActivity {

    EditText age,ht,wt,gen;
    String A,H,W,G;
    Button Sub;

    SharedPreferences sref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);

        sref=getSharedPreferences("Info",MODE_PRIVATE);


        age=findViewById(R.id.et_Age);
        ht=findViewById(R.id.et_height);
        wt=findViewById(R.id.et_weight);
        gen=findViewById(R.id.et_gender);
        Sub = findViewById(R.id.submit);




        Sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                A=age.getText().toString();
                H=ht.getText().toString();
                W=wt.getText().toString();
                G=gen.getText().toString();

                if(A.equals("") || H.equals("") || W.equals("") || G.equals("")){
                    Toast.makeText(getApplicationContext(),"Please Provide All Details to continue",Toast.LENGTH_SHORT).show();
                }
                else {

                    SharedPreferences.Editor e = sref.edit();
                    e.putInt("Age", Integer.parseInt(A));
                    e.commit();
                    e.putInt("Height", Integer.parseInt(H));
                    e.commit();
                    e.putInt("Weight", Integer.parseInt(W));
                    e.commit();
                    e.putString("Gender", G);
                    e.commit();

                    finishAffinity();
                    startActivity(new Intent(PatientInfoActivity.this, MainActivity.class));
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"Please Enter the Details",Toast.LENGTH_SHORT).show();
    }
}
