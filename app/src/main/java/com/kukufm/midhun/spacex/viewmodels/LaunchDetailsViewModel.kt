package com.kukufm.midhun.spacex.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kukufm.midhun.spacex.models.LaunchInfoModel
import com.kukufm.midhun.spacex.models.room.FavTable
import com.kukufm.midhun.spacex.repository.Repository
import com.kukufm.midhun.spacex.repository.room.LocalDatabase
import kotlinx.coroutines.launch

class LaunchDetailsViewModel : ViewModel() {

    val repo = Repository()

    val bulkLaunchDetails = repo.bulkLaunchDetails
    val launchDetailsByName = repo.launchDetailsByName

    val likedStatus = repo.likedStatus

    var selectedLaunchDetailItem : LaunchInfoModel? = null
        private set


    fun getAllLaucheDetails(){
        viewModelScope.launch {
            repo.getAllLaucheDetails()
        }
    }

    fun getLaunchDetailsByName(mission_name : String){
        viewModelScope.launch {
            repo.getLaunchDetailsByName(mission_name)
        }
    }

    fun getAllLaunchDetailsFromLocal(localDatabase: LocalDatabase){
        viewModelScope.launch {
            repo.getAllLaunchDetailsFromLocal(localDatabase)
        }
    }

    fun getLaunchDetailsByNameFromLocal(mission_name : String, localDatabase: LocalDatabase){
        viewModelScope.launch {
            repo.getLaunchDetailsByNameFromLocal(mission_name, localDatabase)
        }
    }

    fun checkIfAlreadyLiked(flightNumber : Int, localDatabase: LocalDatabase){
        viewModelScope.launch {
            repo.checkIfCharacterFavourite(flightNumber,localDatabase)
        }
    }

    fun addToFavourites(favModel : FavTable, localDatabase: LocalDatabase){
        viewModelScope.launch {
            repo.addToFavourites(favModel, localDatabase)
        }
    }

    fun addLaunchInfoModel(model : List<LaunchInfoModel>, localDatabase: LocalDatabase){
        viewModelScope.launch {
            repo.addLaunchInfoModel(model, localDatabase)
        }
    }

    fun removeFromFavourites(favModel : FavTable, localDatabase: LocalDatabase){
        viewModelScope.launch {
            repo.removeFromFavourites(favModel, localDatabase)
        }
    }

    fun updateSelectedLaunchDetailItem(model : LaunchInfoModel){
        selectedLaunchDetailItem = model
    }

}