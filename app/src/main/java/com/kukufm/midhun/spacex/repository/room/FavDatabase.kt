package com.kukufm.midhun.spacex.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kukufm.midhun.spacex.models.room.FavTable
import com.kukufm.midhun.spacex.repository.localapi.FavLocalApi

@Database(entities = [FavTable::class], version = 1)
abstract class FavDatabase : RoomDatabase() {

    companion object{

        private var databaseInstance : FavDatabase? = null

        fun getDatabase(context : Context) : FavDatabase {
            if(databaseInstance ==null){
                synchronized(this){
                    databaseInstance = Room.databaseBuilder(context.applicationContext,
                        FavDatabase::class.java
                        ,"favtable")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return databaseInstance!!
        }
    }

    abstract fun getFavLocalApi() : FavLocalApi

}