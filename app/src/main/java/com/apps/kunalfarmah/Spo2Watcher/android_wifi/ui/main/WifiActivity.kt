package com.apps.kunalfarmah.Spo2Watcher.android_wifi.ui.main

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.*
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.apps.kunalfarmah.Spo2Watcher.android_wifi.Pref
import com.apps.kunalfarmah.Spo2Watcher.android_wifi.app.MyData
import com.apps.kunalfarmah.Spo2Watcher.android_wifi.app.TofahaApplication
import com.apps.kunalfarmah.Spo2Watcher.android_wifi.ui.FloatingMenuFragment
import com.apps.kunalfarmah.Spo2Watcher.android_wifi.ui.InfoActivity
import com.apps.kunalfarmah.Spo2Watcher.activity.ArduinoActivity
import com.apps.kunalfarmah.Spo2Watcher.R
import kotlinx.android.synthetic.main.activity_wifi.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.*
import java.net.Socket
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

    lateinit var openConn:OpenConnection
    lateinit var sendMsg:SendMessages

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        (this.application as TofahaApplication).getAppComponent()!!.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi)

        ButterKnife.bind(this)

        MyData.wifiActivity = this

        dialog = Dialog(this)
        dialog!!.setTitle(getString(R.string.waiting_dialog))
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
        if(ssidText.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "SSID can't be Empty", Toast.LENGTH_SHORT).show()
            return
        }
        if(passwordText.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Password can't be Empty", Toast.LENGTH_SHORT).show()
            return
        }

        sendMessage(String.format("SSID: %s, Password: %s", ssidText.text.toString().trim(), passwordText.text.toString().trim()))
    }

    override fun sendMessage(msg: String) {
        sendMsg = SendMessages(msg,this)
        sendMsg!!.execute()
        println("Message: $msg")
        dialog!!.show()
        handler.postDelayed(dialogRunnable,10*1000)
    }

    override fun openConnection() {
        try {
            openConn = OpenConnection(pref.ipAddress!!, pref.portNumber,this)
            openConn!!.execute()
        }catch (e:Exception){
            Toast.makeText(this,"Host is unreachable, please check your ip address and try again",Toast.LENGTH_SHORT).show()
            reset()
        }
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
        var closeConnection = CLoseConnection(this)
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
        if(null!=sendMsg && !sendMsg.isCancelled)
            sendMsg.cancel(true)
        if(null!=openConn && !openConn.isCancelled)
            openConn.cancel(true)

        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.wifi_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId==R.id.change_ip_details){
            reset()
        }
        return true
    }

    fun reset(){
        if(null!=sendMsg && !sendMsg.isCancelled)
            sendMsg.cancel(true)
        if(null!=openConn && !openConn.isCancelled)
            openConn.cancel(true)
        var intent = Intent(applicationContext,InfoActivity::class.java)
        intent.putExtra("clear",true)
        startActivity(intent)
        finish()
    }

    fun resetWithMessage(msg:String){
        if(null!=sendMsg && !sendMsg.isCancelled)
            sendMsg.cancel(true)
        if(null!=openConn && !openConn.isCancelled)
            openConn.cancel(true)
        var intent = Intent(applicationContext,InfoActivity::class.java)
        intent.putExtra("clear",true)
        intent.putExtra("msg",msg)
        startActivity(intent)
        finish()
    }


    class OpenConnection(private val ipAddress: String, private val portNumber: Int, context:Activity) : AsyncTask<Void, String, Void>() {

        internal var context:Activity?=null
        init {
            this.context = context
        }
        override fun doInBackground(vararg voids: Void): Void?{
            try {
                MyData.socket = Socket(ipAddress, portNumber)
                MyData.socket!!.soTimeout= 120000
                System.out.println("connection opened")

                if (MyData.THREAD_RUNNING == false){
                    MyData.wifiActivity.receiveMessage()
                }

            } catch (e: IOException) {
                e.printStackTrace()
                (context as WifiActivity).resetWithMessage(e.localizedMessage.toString())
            }

            return null
        }
    }

    class SendMessages(msg: String, context:Activity) : AsyncTask<Void, String, Void>() {

        internal var msg = ""
        internal var context:Activity?=null

        init {
            this.msg = msg
            this.context = context
        }

        override fun doInBackground(vararg voids: Void): Void? {
            try {

                val out = PrintWriter(BufferedWriter(OutputStreamWriter(MyData.socket
                !!.getOutputStream())), true)

                out.println(msg)
                println("message send")

            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(context,e.localizedMessage.toString(),Toast.LENGTH_SHORT).show()
                (context as WifiActivity).resetWithMessage(e.localizedMessage.toString())
            }

            return null
        }
    }

    class CLoseConnection(context:Activity) : AsyncTask<Void , String , Void>(){

        internal var context:Activity?=null
        init{
            this.context=context
        }
        override fun doInBackground(vararg params: Void?): Void? {
            try {

                if(MyData.socket!=null) {
                    MyData.socket!!.close()
                    System.out.println("connection closed")
                }

            }catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(context,e.localizedMessage.toString(),Toast.LENGTH_SHORT).show()
                (context as WifiActivity).resetWithMessage(e.localizedMessage.toString())
            }

            return null
        }

    }


}
