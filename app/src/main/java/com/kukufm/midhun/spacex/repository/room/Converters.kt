package com.kukufm.midhun.spacex.repository.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kukufm.midhun.spacex.models.LaunchSite
import com.kukufm.midhun.spacex.models.Links
import com.kukufm.midhun.spacex.models.Rocket
import com.kukufm.midhun.spacex.models.Telemetry
import com.kukufm.midhun.spacex.models.Timeline

class Converters {

    @TypeConverter
    fun encodeLaunchSite(model : LaunchSite) : String{
        val convertedModel = Gson().toJson(model)
        return convertedModel
    }

    @TypeConverter
    fun decodeLaunchSite(encodedString : String) : LaunchSite {
        val model = Gson().fromJson(encodedString, LaunchSite::class.java)
        return model
    }

    @TypeConverter
    fun encodeLinks(model : Links) : String{
        val convertedModel = Gson().toJson(model)
        return convertedModel
    }

    @TypeConverter
    fun decodeLinks(encodedString : String) : Links {
        val model = Gson().fromJson(encodedString, Links::class.java)
        return model
    }

    @TypeConverter
    fun encodeList(model : List<String>) : String{
        val convertedModel = Gson().toJson(model)
        return convertedModel
    }

    @TypeConverter
    fun decodeList(encodedString : String) : List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        val model = Gson().fromJson<List<String>>(encodedString, listType)
        return model
    }

    @TypeConverter
    fun encodeRocket(model : Rocket) : String{
        val convertedModel = Gson().toJson(model)
        return convertedModel
    }

    @TypeConverter
    fun decodeRocket(encodedString : String) : Rocket {
        val model = Gson().fromJson(encodedString, Rocket::class.java)
        return model
    }

    @TypeConverter
    fun encodeTelemetry(model : Telemetry) : String{
        val convertedModel = Gson().toJson(model)
        return convertedModel
    }

    @TypeConverter
    fun decodeTelemetry(encodedString : String) : Telemetry {
        val model = Gson().fromJson(encodedString, Telemetry::class.java)
        return model
    }

    @TypeConverter
    fun encodeTimeline(model : Timeline?) : String{
        val convertedModel = Gson().toJson(model)
        return convertedModel
    }

    @TypeConverter
    fun decodeTimeline(encodedString : String) : Timeline? {
        val model = Gson().fromJson(encodedString, Timeline::class.java)
        return model
    }

}