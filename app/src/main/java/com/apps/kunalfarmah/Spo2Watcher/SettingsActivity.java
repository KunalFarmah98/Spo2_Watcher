package com.apps.kunalfarmah.Spo2Watcher;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    EditText age,wt,ht,gender;
    TextView name;
    SharedPreferences sref;
    Button Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Submit = findViewById(R.id.update);

        sref = getSharedPreferences("Info",MODE_PRIVATE);

        name=findViewById(R.id.et_name);
        name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString());
        age=findViewById(R.id.et_age);
        age.setText(String.valueOf(sref.getInt("Age",20)));
        ht=findViewById(R.id.et_height);
        ht.setText(String.valueOf(sref.getInt("Height",178)));
        wt=findViewById(R.id.et_weight);
        wt.setText(String.valueOf(sref.getInt("Weight",80)));
        gender=findViewById(R.id.et_gender);
        gender.setText(sref.getString("Gender","M"));

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor e =sref.edit();
//                e.putString("Name",name.getText().toString());
//                e.commit();


                e.putInt("Age",Integer.parseInt(age.getText().toString()));
                e.commit();

                e.putInt("Weight",Integer.parseInt(wt.getText().toString()));
                e.commit();

                e.putInt("Height",Integer.parseInt(ht.getText().toString()));
                e.commit();

                e.putString("Gender",gender.getText().toString());
                e.commit();


              finish();

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
