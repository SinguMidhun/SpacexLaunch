package com.kukufm.midhun.spacex.repository.localapi

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kukufm.midhun.spacex.models.LaunchInfoModel
import com.kukufm.midhun.spacex.models.room.FavTable
import retrofit2.Response
import retrofit2.http.GET

@Dao
interface LocalApi {

    @Query("SELECT EXISTS(SELECT * FROM favtable WHERE flightNumber = :id)")
    suspend fun checkIfLiked(id : Int) : Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFav(table : FavTable) : Unit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllLaunchInfoModel(model : List<LaunchInfoModel>) : Unit

    @Query("SELECT * FROM launchinfomodel ORDER BY flight_number")
    suspend fun getAllLaunchDetailsFromLocal() : List<LaunchInfoModel>

    @Query("SELECT * FROM launchinfomodel WHERE mission_name =:mission_name")
    suspend fun getLaunchDetailsByNameFromLocal(mission_name : String) : List<LaunchInfoModel>

    @Delete
    suspend fun removeFromFav(table : FavTable) : Unit

}