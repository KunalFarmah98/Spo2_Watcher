package com.apps.kunalfarmah.Spo2Watcher.Android_wifi.ui.main

import android.graphics.drawable.ColorDrawable
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.annotation.RequiresApi
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.apps.kunalfarmah.Spo2Watcher.Android_wifi.Pref
import com.apps.kunalfarmah.Spo2Watcher.Android_wifi.app.MyData
import com.apps.kunalfarmah.Spo2Watcher.Android_wifi.app.TofahaApplication
import com.apps.kunalfarmah.Spo2Watcher.Android_wifi.connection.CLoseConnection
import com.apps.kunalfarmah.Spo2Watcher.Android_wifi.connection.OpenConnection
import com.apps.kunalfarmah.Spo2Watcher.Android_wifi.connection.SendMessages
import com.apps.kunalfarmah.Spo2Watcher.Android_wifi.ui.FloatingMenuFragment
import com.apps.kunalfarmah.Spo2Watcher.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_wifi.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.*
import javax.inject.Inject

class WifiActivity : AppCompatActivity() , MainView {


    var input: BufferedReader? = null

    @Inject
    lateinit var pref: Pref

    @BindView(R.id.text_message)
    lateinit var msgText : EditText

    @BindView(R.id.server_response)
    lateinit var serverResponse : TextView

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        (this.application as TofahaApplication).getAppComponent()!!.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi)

        ButterKnife.bind(this)

        MyData.wifiActivity = this

        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.blue_menu)))
        window.statusBarColor = resources.getColor(R.color.blue_menu)

        this.fab.bringToFront()
        this.fab.parent.requestLayout()

        led1.setOnClickListener({
            var m : String
            if (led1.isChecked)
                m = "pin=11"
            else
                m = "pin=01"
            sendMessage(m)
        })

        led2.setOnClickListener({
            var m : String
            if (led2.isChecked)
                m = "pin=22"
            else
                m = "pin=02"
            sendMessage(m)
        })

        refresh_connection.setOnClickListener({
            openConnection()
        })

    }


    @OnClick(R.id.fab)
    operator fun next() {
        var dialogFrag = FloatingMenuFragment.newInstance()
        dialogFrag.setParentFab(fab)
        dialogFrag.show(getSupportFragmentManager(), dialogFrag.getTag())
    }

    @OnClick(R.id.send_message)
    fun sendButton(){
        sendMessage(msgText.text.toString())
    }

    override fun sendMessage(msg : String) {
        var sendMessages = SendMessages(msg)
        sendMessages!!.execute()
    }

    override fun openConnection() {
        var openConnection = OpenConnection(pref.ipAddress!!, pref.portNumber)
        openConnection!!.execute()
    }

    override fun receiveMessage() {
        doAsync {

            input = BufferedReader(InputStreamReader(MyData.socket!!.getInputStream()))
            var msgText = "waiting ...."
            MyData.THREAD_RUNNING = true

            while (true) {

                //println(input?.readLine())
                Thread.sleep(300)
                msgText = input?.readLine().toString()

                uiThread {
                    serverResponse.text = msgText
                }
            }
        }
    }

    override fun closeConnection() {
        var closeConnection = CLoseConnection()
        closeConnection!!.execute()
    }


    override fun onResume() {
        super.onResume()
        openConnection()
    }

    override fun onPause() {
        super.onPause()
        closeConnection()
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

}
