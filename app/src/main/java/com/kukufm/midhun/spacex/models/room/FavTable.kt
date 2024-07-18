package com.kukufm.midhun.spacex.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favtable")
data class FavTable(
    @PrimaryKey
    val flightNumber : Int
)
