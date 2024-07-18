package com.kukufm.midhun.spacex.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

object Network {

    private var connectivityManager : ConnectivityManager? = null

    fun setUpConnectivityManager(context : Context){
        if(connectivityManager==null){
            synchronized(this){
                connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            }
        }
    }

    fun observeNetworkStatus() : Flow<NetworkStatus> {
        return callbackFlow<NetworkStatus> {
            val networkRequest = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build()

            val networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch {
                        send(NetworkStatus.AVAILABLE)
                    }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch {
                        send(NetworkStatus.UNAVAILABLE)
                    }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(NetworkStatus.LOSING) }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch {
                        send(NetworkStatus.LOSING)
                    }
                }
            }
            connectivityManager?.let {
                it.requestNetwork(networkRequest, networkCallback)
            }

            awaitClose {
                connectivityManager?.unregisterNetworkCallback(networkCallback)
            }
        }.distinctUntilChanged()
    }

}

enum class NetworkStatus{
    LOSING,
    UNAVAILABLE,
    AVAILABLE
}