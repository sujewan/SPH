package com.sujewan.sph.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sujewan.sph.model.Quarter
import java.util.*

class RoomConverters {
    var gson = Gson()

    @TypeConverter
    fun stringToItemList(data: String?): ArrayList<Quarter> {
        if (data == null) {
            return arrayListOf()
        }

        val listType = object : TypeToken<ArrayList<Quarter>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun itemListToString(items: ArrayList<Quarter>?): String? {
        if(items == null) {
            return null
        }
        return gson.toJson(items)
    }
}