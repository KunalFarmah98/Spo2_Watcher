package com.apps.kunalfarmah.Spo2Watcher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity {

    private String permission_String[] = new String[]{
            "android.permission.CAMERA",
            "android.permission.FLASHLIGHT",
            "android.permission.WAKE_LOCK",
            "android.permission.ACCESS_NETWORK_STATE",
            "android.permission.READ_SMS",
            "android.permission.RECEIVE_SMS",
            "android.permission.CALL_PHONE"
    };
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sPref = getSharedPreferences("Data",MODE_PRIVATE);
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.rootnet.in/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        retrofit.create(Api.class).getNumbers().enqueue(new Callback<HelpLineResponse>() {
            @Override
            public void onResponse(Call<HelpLineResponse> call, Response<HelpLineResponse> response) {
                try {
                    sPref.edit().putString("Helpline", new Gson().toJson(response.body().getData().getContacts().getRegional())).apply();
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<HelpLineResponse> call, Throwable t) {

            }
        });

        if (!(checkCallingOrSelfPermission(permission_String[0]) == PackageManager.PERMISSION_GRANTED
                && checkCallingOrSelfPermission(permission_String[1]) == PackageManager.PERMISSION_GRANTED
                && checkCallingOrSelfPermission(permission_String[2]) == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, permission_String, 131);
        } else
            DisplayActivity();
    }

    void DisplayActivity() {
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                SharedPreferences sref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                if (sref.getBoolean("isDoctor", false))
                    startActivity(new Intent(SplashActivity.this, DoctorActivity.class));
                else
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        };

        handler.postDelayed(r, 1000);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 131:
                if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                ) {

                    DisplayActivity();

                } else {
                    Toast.makeText(this, "Please Grant All the Permissions To Continue", Toast.LENGTH_SHORT).show();
                    finish();
                }

                return;
        }

    }


}
