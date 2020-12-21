package com.apps.kunalfarmah.Spo2Watcher.android_wifi.dagger

import com.apps.kunalfarmah.Spo2Watcher.android_wifi.ui.FloatingMenuFragment
import com.apps.kunalfarmah.Spo2Watcher.android_wifi.ui.InfoActivity
import com.apps.kunalfarmah.Spo2Watcher.android_wifi.ui.main.WifiActivity

import javax.inject.Singleton

import dagger.Component

@Singleton
@Component(modules = arrayOf(AppModule::class , PreferenceModule :: class))
interface AppComponent {

    fun inject(infoActivity: InfoActivity)
    fun inject(wifiActivity: WifiActivity)
    fun inject(target: FloatingMenuFragment)

}
