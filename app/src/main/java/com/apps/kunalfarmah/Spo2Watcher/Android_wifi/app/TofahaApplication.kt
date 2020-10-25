package com.apps.kunalfarmah.Spo2Watcher.Android_wifi.app

import android.app.Application
import com.apps.kunalfarmah.Spo2Watcher.Android_wifi.dagger.AppComponent
import com.apps.kunalfarmah.Spo2Watcher.Android_wifi.dagger.AppModule
import com.apps.kunalfarmah.Spo2Watcher.Android_wifi.dagger.DaggerAppComponent

/**
 * Created by ayoub on 11/23/17.
 */
class TofahaApplication : Application() {

    private var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger(this)
    }

    protected fun initDagger(application: TofahaApplication): AppComponent {
        return DaggerAppComponent.builder().appModule(AppModule(application)).build()
    }

    fun getAppComponent(): AppComponent? {
        return this.appComponent
    }
}