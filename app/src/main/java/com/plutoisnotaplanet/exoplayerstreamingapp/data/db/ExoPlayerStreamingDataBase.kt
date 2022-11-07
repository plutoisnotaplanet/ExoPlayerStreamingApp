package com.plutoisnotaplanet.exoplayerstreamingapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.plutoisnotaplanet.exoplayerstreamingapp.data.db.converters.Converters
import com.plutoisnotaplanet.exoplayerstreamingapp.data.db.dao.ChannelsDao
import com.plutoisnotaplanet.exoplayerstreamingapp.data.db.entities.ChannelEntity

@Database(
    entities = [
        ChannelEntity::class
    ], version = 3,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class ExoPlayerStreamingDataBase : RoomDatabase() {

    companion object {
        const val CHANNELS_DB = "channels_db"
    }

    abstract val channelsDao: ChannelsDao
}