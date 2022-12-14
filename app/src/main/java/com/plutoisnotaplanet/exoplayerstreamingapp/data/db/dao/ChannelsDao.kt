package com.plutoisnotaplanet.exoplayerstreamingapp.data.db.dao

import androidx.room.*
import com.plutoisnotaplanet.exoplayerstreamingapp.data.db.entities.ChannelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChannelsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(value: ChannelEntity)

    @Query("DELETE FROM channels_db WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM channels_db")
    suspend fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(value: ChannelEntity)

    @Query("SELECT EXISTS(SELECT * FROM channels_db WHERE id =:id)")
    suspend fun hasChannel(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<ChannelEntity>)

    @Query("SELECT * FROM channels_db WHERE id = :id")
    suspend fun getChannelById(id: Int): ChannelEntity

    @Query("SELECT * FROM channels_db")
    fun getAllChannelsFLow(): Flow<List<ChannelEntity>>

    @Query("SELECT * FROM channels_db")
    fun getAllChannels(): List<ChannelEntity>

    @Transaction
    suspend fun save(value: ChannelEntity) {
        val exist = hasChannel(value.id)
        if (exist) {
            val currentChannel = getChannelById(value.id)
            val updatedChannel = value.copy(isFavorite = currentChannel.isFavorite)
            update(updatedChannel)
        } else insert(value)
    }

    @Transaction
    suspend fun setFavorState(id: Int, isFavorite: Boolean) {
        val channel = getChannelById(id)
        channel.isFavorite = isFavorite
        update(channel)
    }

    @Transaction
    suspend fun save(list: List<ChannelEntity>) {
        if (list.isEmpty()) return

        list.forEach {
            save(it)
        }
    }
}