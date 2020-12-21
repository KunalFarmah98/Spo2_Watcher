package com.apps.kunalfarmah.Spo2Watcher.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.apps.kunalfarmah.Spo2Watcher.android_wifi.ui.InfoActivity
import com.apps.kunalfarmah.Spo2Watcher.R

class OptionsActivity : AppCompatActivity() {

    var ppg: Button?=null
    var wifi:Button?=null
    var sharedPreferences:SharedPreferences?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        supportActionBar!!.title = "Measure Vitals"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        sharedPreferences = getSharedPreferences("measurement_type", Context.MODE_PRIVATE);

        ppg = findViewById(R.id.ppg)

        ppg!!.setOnClickListener {
            sharedPreferences!!.edit().putBoolean("isPPG",true).apply();
            startActivity(Intent(this, VitalSignsActivity::class.java))
        }
        wifi = findViewById(R.id.wifi)

        wifi!!.setOnClickListener {
            sharedPreferences!!.edit().putBoolean("isPPG",false).apply();
//            startActivity(Intent(this, ArduinoActivity::class.java))
            startActivity(Intent(this, InfoActivity::class.java))

        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}