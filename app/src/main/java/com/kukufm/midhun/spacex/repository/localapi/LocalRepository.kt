package com.kukufm.midhun.spacex.repository.localapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kukufm.midhun.spacex.models.room.FavTable
import com.kukufm.midhun.spacex.repository.room.FavDatabase
import com.kukufm.midhun.spacex.utils.NetworkStates

class LocalRepository {

    private val _likedStatus = MutableLiveData<NetworkStates<Boolean>>(NetworkStates.Loading())
    val likedStatus : LiveData<NetworkStates<Boolean>> get() = _likedStatus

    suspend fun checkIfCharacterFavourite(flightNumber : Int, roomInstance : FavDatabase){
        _likedStatus.postValue(NetworkStates.Loading())
        val response = roomInstance.getFavLocalApi().checkIfLiked(flightNumber)
        if(response!=null){
            _likedStatus.postValue(NetworkStates.Success(response))
        }else{
            _likedStatus.postValue(NetworkStates.Error("Something went wrong"))
        }
    }

    suspend fun addToFavourites(favModel : FavTable, favDatabase: FavDatabase){
        favDatabase.getFavLocalApi().addToFav(favModel)
    }

    suspend fun removeFromFavourites(favModel : FavTable, favDatabase: FavDatabase){
        favDatabase.getFavLocalApi().removeFromFav(favModel)
    }

}