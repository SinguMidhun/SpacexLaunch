package com.kukufm.midhun.spacex

import android.app.Application
import com.kukufm.midhun.spacex.utils.Network
import com.kukufm.midhun.spacex.utils.SharedPreference

class ApplicationClass : Application() {

    override fun onCreate() {
        super.onCreate()
        Network.setUpConnectivityManager(this)
        SharedPreference.setSharedPrefRepoInstance(this)
    }
}