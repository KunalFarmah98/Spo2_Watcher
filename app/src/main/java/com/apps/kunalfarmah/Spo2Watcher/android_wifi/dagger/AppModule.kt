package com.apps.kunalfarmah.Spo2Watcher.android_wifi.dagger

import android.app.Application
import android.content.Context

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return this.application
    }
}
