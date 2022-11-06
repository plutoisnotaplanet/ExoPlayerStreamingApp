package com.plutoisnotaplanet.exoplayerstreamingapp.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.Current
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.ForeignPlayer

object Converters {

    @TypeConverter
    fun toCurrent(current: String): Current {
        val listType = object : TypeToken<Current>() {}.type
        return Gson().fromJson(current, listType)
    }

    @TypeConverter
    fun fromCurrent(current: Current): String {
        val listType = object : TypeToken<Current>() {}.type
        return Gson().toJson(current, listType)
    }

    @TypeConverter
    fun toForeignPlayer(foreignPlayer: String): ForeignPlayer {
        val listType = object : TypeToken<ForeignPlayer>() {}.type
        return Gson().fromJson(foreignPlayer, listType)
    }

    @TypeConverter
    fun fromForeignPlayer(foreignPlayer: ForeignPlayer): String {
        val listType = object : TypeToken<ForeignPlayer>() {}.type
        return Gson().toJson(foreignPlayer, listType)
    }
}