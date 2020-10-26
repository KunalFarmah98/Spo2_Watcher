package com.apps.kunalfarmah.Spo2Watcher

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.apps.kunalfarmah.Spo2Watcher.Android_wifi.ui.InfoActivity

class OptionsActivity : AppCompatActivity() {

    var ppg: Button?=null
    var wifi:Button?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        supportActionBar!!.title = "Measure Vitals"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        ppg = findViewById(R.id.ppg)

        ppg!!.setOnClickListener {
            startActivity(Intent(this, VitalSignsActivity::class.java))
        }
        wifi = findViewById(R.id.wifi)

        wifi!!.setOnClickListener {
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