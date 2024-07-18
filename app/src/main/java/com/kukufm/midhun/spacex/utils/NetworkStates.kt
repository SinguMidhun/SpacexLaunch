package com.kukufm.midhun.spacex.utils

sealed class NetworkStates<T>(val data : T? = null, val message : String? = null){

    class Success<T>(data: T? = null) : NetworkStates<T>(data)
    class Error<T>(message : String) : NetworkStates<T>(message = message)
    class Loading<T>() : NetworkStates<T>()

}