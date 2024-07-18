package com.kukufm.midhun.spacex.repository.remoteapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kukufm.midhun.spacex.models.LaunchInfoModel
import com.kukufm.midhun.spacex.utils.NetworkStates
import com.kukufm.midhun.spacex.utils.RetrofitUtil

class RemoteRepository {

    val TAG = "RemoteRepository"
    val remoteApi by lazy {
        RetrofitUtil.getRetrofitInstance().create(RemoteApi::class.java)
    }

    private val _bulkLaunchDetails = MutableLiveData<NetworkStates<List<LaunchInfoModel>>>(NetworkStates.Loading())
    val bulkLaunchDetails : LiveData<NetworkStates<List<LaunchInfoModel>>> get() = _bulkLaunchDetails

    private val _launchDetailsByName = MutableLiveData<NetworkStates<List<LaunchInfoModel>>>(NetworkStates.Loading())
    val launchDetailsByName : LiveData<NetworkStates<List<LaunchInfoModel>>> get() = _launchDetailsByName

    suspend fun getAllLaucheDetails(){
        val response = remoteApi.getAllLauches()
        if(response.isSuccessful && response.body()!=null){
            _bulkLaunchDetails.postValue(
                NetworkStates.Success(response.body())
            )
        }else{
            Log.e(TAG + " getAllLaucheDetails",response.errorBody().toString())
            _bulkLaunchDetails.postValue(
                NetworkStates.Error("Something went wrong")
            )
        }
    }

    suspend fun getLaunchDetailsByName(mission_name : String){
        val response = remoteApi.getLaunchDetailsByName(mission_name)
        if(response.isSuccessful && response.body()!=null){
            _launchDetailsByName.postValue(
                NetworkStates.Success(response.body())
            )
        }else{
            Log.e(TAG + " getLaunchDetailsByName",response.errorBody().toString())
            _launchDetailsByName.postValue(
                NetworkStates.Error("Something went wrong")
            )
        }
    }

}