package com.kukufm.midhun.spacex.repository.localapi

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kukufm.midhun.spacex.models.room.FavTable

@Dao
interface FavLocalApi {

    @Query("SELECT EXISTS(SELECT * FROM favtable WHERE flightNumber = :id)")
    suspend fun checkIfLiked(id : Int) : Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFav(table : FavTable) : Unit

    @Delete
    suspend fun removeFromFav(table : FavTable) : Unit

}