package com.kukufm.midhun.spacex.repository.remoteapi

import com.kukufm.midhun.spacex.models.LaunchInfoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteApi {

    @GET("launches/")
    suspend fun getAllLauches() : Response<List<LaunchInfoModel>?>

    @GET("launches/")
    suspend fun getLaunchDetailsByName(@Query("mission_name") mission_name : String) : Response<List<LaunchInfoModel>?>

}