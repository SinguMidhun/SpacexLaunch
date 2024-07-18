package com.kukufm.midhun.spacex.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kukufm.midhun.spacex.models.LaunchInfoModel
import com.kukufm.midhun.spacex.models.room.FavTable
import com.kukufm.midhun.spacex.repository.localapi.LocalApi

@Database(entities = [FavTable::class,LaunchInfoModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {

    companion object{

        private var databaseInstance : LocalDatabase? = null

        fun getDatabase(context : Context) : LocalDatabase {
            if(databaseInstance ==null){
                synchronized(this){
                    databaseInstance = Room.databaseBuilder(context.applicationContext,
                        LocalDatabase::class.java
                        ,"localdb")
                        .build()
                }
            }
            return databaseInstance!!
        }
    }

    abstract fun getFavLocalApi() : LocalApi

}