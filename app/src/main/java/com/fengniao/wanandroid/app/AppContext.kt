package com.fengniao.wanandroid.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

@SuppressLint("Registered")
class AppContext : Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }


}