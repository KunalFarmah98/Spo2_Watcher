package com.apps.kunalfarmah.Spo2Watcher.Android_wifi.ui

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.text.format.Formatter
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.apps.kunalfarmah.Spo2Watcher.Android_wifi.Pref
import com.apps.kunalfarmah.Spo2Watcher.Android_wifi.Util
import com.apps.kunalfarmah.Spo2Watcher.Android_wifi.app.TofahaApplication
import com.apps.kunalfarmah.Spo2Watcher.Android_wifi.ui.main.WifiActivity
import com.apps.kunalfarmah.Spo2Watcher.R
import java.util.*
import javax.inject.Inject


class InfoActivity : AppCompatActivity() {

    @BindView(R.id.ip_address)
    lateinit var ipAddress: EditText

    @BindView(R.id.port_number)
    lateinit var portNumber: EditText

    @Inject
    lateinit var pref: Pref

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        (this.application as TofahaApplication).getAppComponent()!!.inject(this)

        ButterKnife.bind(this)

        supportActionBar!!.title = "Measure from Arduino"
        val wm = getApplicationContext().getSystemService(WIFI_SERVICE) as WifiManager
        val ip: String = Formatter.formatIpAddress(wm.connectionInfo.ipAddress)
        ipAddress.setText(ip)

        if (Util.isValidIp(pref!!.ipAddress!!) && pref!!.portNumber != 0) {
            startMainActivity()
        }
    }

    @OnClick(R.id.login_next)
    fun next() {

        if (Util.isValidIp(ipAddress!!.text.toString())) {
            pref!!.ipAddress = ipAddress!!.text.toString()
        } else {
            Toast.makeText(this, "Enter valid IP address", Toast.LENGTH_SHORT).show()
            return
        }

        if (portNumber!!.text.toString() != "") {
            pref!!.portNumber = Integer.parseInt(portNumber!!.text.toString())
        } else {
            Toast.makeText(this, "Enter port number", Toast.LENGTH_SHORT).show()
            return
        }

        startMainActivity()
    }

    fun startMainActivity() {
        val intent = Intent(this@InfoActivity, WifiActivity::class.java)
        startActivity(intent)
    }

}
