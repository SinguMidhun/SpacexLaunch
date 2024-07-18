package com.kukufm.midhun.spacex.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kukufm.midhun.spacex.models.LaunchInfoModel
import com.kukufm.midhun.spacex.models.room.FavTable
import com.kukufm.midhun.spacex.repository.localapi.LocalRepository
import com.kukufm.midhun.spacex.repository.remoteapi.RemoteRepository
import com.kukufm.midhun.spacex.repository.room.FavDatabase
import kotlinx.coroutines.launch

class LaunchDetailsViewModel : ViewModel() {

    val repository = RemoteRepository()
    val localRepository = LocalRepository()

    val bulkLaunchDetails = repository.bulkLaunchDetails
    val launchDetailsByName = repository.launchDetailsByName

    val likedStatus = localRepository.likedStatus

    var selectedLaunchDetailItem : LaunchInfoModel? = null
        private set


    fun getAllLaucheDetails(){
        viewModelScope.launch {
            repository.getAllLaucheDetails()
        }
    }

    fun getLaunchDetailsByName(mission_name : String){
        viewModelScope.launch {
            repository.getLaunchDetailsByName(mission_name)
        }
    }

    fun checkIfAlreadyLiked(flightNumber : Int, favDatabase: FavDatabase){
        viewModelScope.launch {
            localRepository.checkIfCharacterFavourite(flightNumber,favDatabase)
        }
    }

    fun addToFavourites(favModel : FavTable, favDatabase: FavDatabase){
        viewModelScope.launch {
            localRepository.addToFavourites(favModel, favDatabase)
        }
    }

    fun removeFromFavourites(favModel : FavTable, favDatabase: FavDatabase){
        viewModelScope.launch {
            localRepository.removeFromFavourites(favModel, favDatabase)
        }
    }

    fun updateSelectedLaunchDetailItem(model : LaunchInfoModel){
        selectedLaunchDetailItem = model
    }

}