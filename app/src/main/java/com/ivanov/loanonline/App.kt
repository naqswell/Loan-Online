package com.ivanov.loanonline

import android.app.Application
import com.ivanov.loanonline.di.app.AppComponent
import com.ivanov.loanonline.di.app.DaggerAppComponent

class App: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .context(applicationContext)
            .build()    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: App
    }
}