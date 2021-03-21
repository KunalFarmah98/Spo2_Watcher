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
    var edit:Button?=null
    var sharedPreferences:SharedPreferences?=null
    lateinit var detailPrefs: SharedPreferences

    val DETAILS = "DETAILS"
    val NAME = "NAME"
    val NODE_MCU = "NODE_MCU"
    val MCUID = "MCU_ID"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        supportActionBar!!.title = "Measure Vitals"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        sharedPreferences = getSharedPreferences("measurement_type", Context.MODE_PRIVATE);
        detailPrefs = getSharedPreferences(DETAILS,Context.MODE_PRIVATE)

        ppg = findViewById(R.id.ppg)

        ppg!!.setOnClickListener {
            sharedPreferences!!.edit().putBoolean("isPPG",true).apply();
            startActivity(Intent(this, VitalSignsActivity::class.java))
        }
        wifi = findViewById(R.id.wifi)

        if(detailPrefs.getString(NAME,"").isNullOrEmpty()){
            var intent = Intent(this,DetailsActivity::class.java)
            intent.putExtra("editing",false)
            intent.putExtra("redirect",true)
            startActivity(intent)
        }
        wifi!!.setOnClickListener {
            sharedPreferences!!.edit().putBoolean("isPPG",false).apply()
            if(getSharedPreferences(NODE_MCU, MODE_PRIVATE).getString(MCUID,"").isNullOrEmpty()){
                var intent = Intent(this,DetailsActivity::class.java)
                intent.putExtra("editing",false)
                intent.putExtra("redirect",false);
                startActivity(intent)
            }
            else{
                var intent = Intent(this,ArduinoActivity::class.java)
                intent.putExtra("id",getSharedPreferences(NODE_MCU, MODE_PRIVATE).getString(MCUID,""))
                startActivity(intent)
            }
//            startActivity(Intent(this, InfoActivity::class.java))

        }

        edit = findViewById(R.id.edit_mcu_options)
        edit!!.setOnClickListener{
            var intent = Intent(this,DetailsActivity::class.java)
            intent.putExtra("editing",true)
            intent.putExtra("redirect",false);
            startActivity(intent)
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