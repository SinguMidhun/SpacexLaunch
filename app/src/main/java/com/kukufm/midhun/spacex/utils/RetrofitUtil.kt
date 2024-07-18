package com.kukufm.midhun.spacex.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kukufm.midhun.spacex.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtil {
    private var retrofit: Retrofit? = null

    fun getRetrofitInstance(): Retrofit {
        if (retrofit == null) {
            synchronized(this) {

                val gson = GsonBuilder()
                    .setLenient()
                    .create()

                retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(BASE_URL)
                    .build()
            }
        }
        return retrofit!!
    }
}