package com.kukufm.midhun.spacex.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.kukufm.midhun.spacex.utils.Constants.LOCAL_SAVED

object SharedPreference {

    private var sharedPrefrencesRepo : SharedPreferences? = null

    fun setSharedPrefRepoInstance(context: Context){
        if(sharedPrefrencesRepo==null){
            synchronized(this){
                sharedPrefrencesRepo = context
                    .getSharedPreferences("InnerSharedPref", Context.MODE_PRIVATE)
            }
        }
    }

    fun getSharedPrefRepoInstance() : SharedPreferences{
        return sharedPrefrencesRepo!!
    }

    fun dataSavedToLocal(isSaved: Boolean){
        val edit = sharedPrefrencesRepo!!.edit()
        edit.putBoolean(LOCAL_SAVED,isSaved)
        edit.apply()
    }

    fun isDataSavedToLocal() : Boolean{
        return sharedPrefrencesRepo!!.getBoolean(LOCAL_SAVED,false)
    }

}