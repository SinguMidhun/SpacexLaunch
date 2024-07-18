package com.kukufm.midhun.spacex

import android.app.Application
import com.kukufm.midhun.spacex.utils.Network

class ApplicationClass : Application() {

    override fun onCreate() {
        super.onCreate()
        Network.setUpConnectivityManager(this)
    }
}