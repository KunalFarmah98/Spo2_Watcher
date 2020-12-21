package com.apps.kunalfarmah.Spo2Watcher.Android_wifi.ui.main

import android.app.Dialog
import android.content.Intent
import android.os.*
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.HandlerCompat.postDelayed
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
import com.apps.kunalfarmah.Spo2Watcher.Android_wifi.ui.InfoActivity
import com.apps.kunalfarmah.Spo2Watcher.ArduinoActivity
import com.apps.kunalfarmah.Spo2Watcher.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_wifi.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.*
import java.util.zip.Inflater
import javax.inject.Inject


class WifiActivity : AppCompatActivity() , MainView {


    var input: BufferedReader? = null
    lateinit var nextIntent: Intent
    var dialog: Dialog?=null
    lateinit var handler:Handler
    lateinit var dialogRunnable:Runnable

    @Inject
    lateinit var pref: Pref

    @BindView(R.id.ssid)
    lateinit var ssidText : EditText

    @BindView(R.id.password)
    lateinit var passwordText : EditText

    @BindView(R.id.server_response)
    lateinit var serverResponse : TextView

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        (this.application as TofahaApplication).getAppComponent()!!.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi)

        ButterKnife.bind(this)

        MyData.wifiActivity = this

        dialog = Dialog(this)
        dialog!!.setTitle("Waiting for Arduino to acknowlege...")
        dialog!!.setContentView(R.layout.please_wait)
        dialog!!.setCancelable(false)

        this.fab.bringToFront()
        this.fab.parent.requestLayout()

        refresh_connection.setOnClickListener({
            openConnection()
        })


        handler = Handler()
        dialogRunnable = Runnable {dialog!!.dismiss()}

    }


    @OnClick(R.id.fab)
    operator fun next() {
        var dialogFrag = FloatingMenuFragment.newInstance()
        dialogFrag.setParentFab(fab)
        dialogFrag.show(getSupportFragmentManager(), dialogFrag.getTag())
    }

    @OnClick(R.id.send_message)
    fun sendButton(){
        sendMessage(String.format("%s\n%s", ssidText.text.toString().trim(), passwordText.text.toString().trim()))
    }

    override fun sendMessage(msg: String) {
        var sendMessages = SendMessages(msg)
        sendMessages!!.execute()
        dialog!!.show()
        handler.postDelayed(dialogRunnable,10*1000)
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
                    if(!msgText.equals("waiting ....")) {
                        serverResponse.text = msgText
                        nextIntent = Intent(applicationContext, ArduinoActivity::class.java);
                        nextIntent.putExtra("id", msgText);
                        dialog!!.hide()
                        startActivity(intent)
                        finish()
                    }
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
        handler.removeCallbacks(dialogRunnable)
        super.onBackPressed()
    }

    override fun onDestroy() {
        handler.removeCallbacks(dialogRunnable)
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.wifi_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId==R.id.change_ip_details){
            var intent = Intent(applicationContext,InfoActivity::class.java)
            intent.putExtra("clear",true)
            startActivity(intent)
            finish()
        }
        return true
    }

}
