package com.kukufm.midhun.spacex.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kukufm.midhun.spacex.models.LaunchInfoModel
import com.kukufm.midhun.spacex.models.room.FavTable
import com.kukufm.midhun.spacex.repository.remoteapi.RemoteApi
import com.kukufm.midhun.spacex.repository.room.LocalDatabase
import com.kukufm.midhun.spacex.utils.NetworkStates
import com.kukufm.midhun.spacex.utils.RetrofitUtil
import com.kukufm.midhun.spacex.utils.SharedPreference

class Repository {

    val TAG = "Repository"

    val remoteApi by lazy {
        RetrofitUtil.getRetrofitInstance().create(RemoteApi::class.java)
    }

    private val _bulkLaunchDetails =
        MutableLiveData<NetworkStates<List<LaunchInfoModel>>>(NetworkStates.Loading())
    val bulkLaunchDetails: LiveData<NetworkStates<List<LaunchInfoModel>>> get() = _bulkLaunchDetails

    private val _launchDetailsByName =
        MutableLiveData<NetworkStates<List<LaunchInfoModel>>>(NetworkStates.Loading())
    val launchDetailsByName: LiveData<NetworkStates<List<LaunchInfoModel>>> get() = _launchDetailsByName

    private val _likedStatus = MutableLiveData<NetworkStates<Boolean>>(NetworkStates.Loading())
    val likedStatus: LiveData<NetworkStates<Boolean>> get() = _likedStatus

    suspend fun checkIfCharacterFavourite(flightNumber: Int, roomInstance: LocalDatabase) {
        _likedStatus.postValue(NetworkStates.Loading())
        val response = roomInstance.getFavLocalApi().checkIfLiked(flightNumber)
        if (response != null) {
            _likedStatus.postValue(NetworkStates.Success(response))
        } else {
            _likedStatus.postValue(NetworkStates.Error("Something went wrong"))
        }
    }

    suspend fun getAllLaucheDetails() {
        Log.d(TAG, "Remote Api Called")
        kotlin.runCatching {
            val response = remoteApi.getAllLauches()
            if (response.isSuccessful && response.body() != null) {
                _bulkLaunchDetails.postValue(
                    NetworkStates.Success(response.body())
                )
            } else {
                Log.e(TAG + " getAllLaucheDetails", response.errorBody().toString())
                _bulkLaunchDetails.postValue(
                    NetworkStates.Error("Something went wrong")
                )
            }
        }
    }

    suspend fun getAllLaunchDetailsFromLocal(localDatabase: LocalDatabase) {
        Log.d(TAG, "Local Api Called")
        kotlin.runCatching {
            localDatabase.getFavLocalApi().getAllLaunchDetailsFromLocal().let {
                Log.d(TAG, "$it")
                if(it.isNotEmpty()){
                    _bulkLaunchDetails.postValue(NetworkStates.Success(it))
                }
            }
        }.onFailure {
            _bulkLaunchDetails.postValue(
                NetworkStates.Error(it.message.toString())
            )
        }
    }

    suspend fun getLaunchDetailsByName(mission_name: String) {
        Log.d(TAG, "Remote Api for search by name is called")
        kotlin.runCatching {
            val response = remoteApi.getLaunchDetailsByName(mission_name)
            if (response.isSuccessful && response.body() != null) {
                _launchDetailsByName.postValue(
                    NetworkStates.Success(response.body())
                )
            } else {
                Log.e("$TAG getLaunchDetailsByName", response.errorBody().toString())
                _launchDetailsByName.postValue(
                    NetworkStates.Error("Something went wrong")
                )
            }
        }
    }

    suspend fun getLaunchDetailsByNameFromLocal(mission_name : String, localDatabase: LocalDatabase) {
        Log.d(TAG, "Local Api for search by name is called")
        kotlin.runCatching {
            val result = localDatabase.getFavLocalApi().getLaunchDetailsByNameFromLocal(mission_name)
            result?.let {
                _launchDetailsByName.postValue(
                    NetworkStates.Success(it)
                )
            }
        }.onFailure {
            _launchDetailsByName.postValue(
                NetworkStates.Error(it.message.toString())
            )
        }
    }

    suspend fun addToFavourites(favModel: FavTable, localDatabase: LocalDatabase) {
        kotlin.runCatching {
            localDatabase.getFavLocalApi().addToFav(favModel)
        }.onFailure {
            Log.e(TAG,"addToFavourites - failed")
        }
    }

    suspend fun addLaunchInfoModel(model: List<LaunchInfoModel>, localDatabase: LocalDatabase) {
        kotlin.runCatching {
            localDatabase.getFavLocalApi().addAllLaunchInfoModel(model).also {
                SharedPreference.dataSavedToLocal(true)
            }
        }.onFailure {
            Log.e(TAG,"addLaunchInfoModel - failed")
        }
    }

    suspend fun removeFromFavourites(favModel: FavTable, localDatabase: LocalDatabase) {
        kotlin.runCatching {
            localDatabase.getFavLocalApi().removeFromFav(favModel)
        }.onFailure {
            Log.e(TAG,"removeFromFavourites - failed")
        }
    }

}