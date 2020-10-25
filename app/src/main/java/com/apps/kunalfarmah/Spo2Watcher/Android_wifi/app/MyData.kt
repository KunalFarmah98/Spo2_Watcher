package com.apps.kunalfarmah.Spo2Watcher.Android_wifi.app

import com.apps.kunalfarmah.Spo2Watcher.Android_wifi.ui.main.WifiActivity
import java.net.Socket

/**
 * Created by ayoub on 11/26/17.
 */

object MyData {
    var socket: Socket?=null
    lateinit var wifiActivity : WifiActivity
    var THREAD_RUNNING = false
}
