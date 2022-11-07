package com.plutoisnotaplanet.exoplayerstreamingapp.data.repository_impl

import com.plutoisnotaplanet.exoplayerstreamingapp.data.db.ExoPlayerStreamingDataBase
import com.plutoisnotaplanet.exoplayerstreamingapp.data.db.dao.ChannelsDao
import com.plutoisnotaplanet.exoplayerstreamingapp.data.repostiory.ChannelsRepository
import com.plutoisnotaplanet.exoplayerstreamingapp.data.rest.Api
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import kotlin.error

class ChannelsRepositoryImpl @Inject constructor(
    private val api: Api,
    dataBase: ExoPlayerStreamingDataBase
): ChannelsRepository {

    private val channelsDao: ChannelsDao = dataBase.channelsDao

    override suspend fun observePlayList(): Flow<Response<List<ChannelShortModel>>> {
        return channelsDao.getAllChannelsFLow().map { list ->
            var playlist = list.map { it.toShortModel() }
            if (playlist.isEmpty()) {
                try {
                    val response = api.loadPlayList().playList
                    playlist = response.map { it.toShortModel() }
                    channelsDao.save(response.map { it.toDbEntity() })

                    success(playlist)
                } catch (e: Exception) {
                    error(e)
                }
            } else {
                success(playlist)
            }
        }
    }

    override suspend fun updatePlayList() {
        val response = api.loadPlayList().playList
        channelsDao.save(response.map { it.toDbEntity() })
    }

    override suspend fun changeFavoriteStatus(id: Int) {
        val channel = channelsDao.getChannelById(id)
        channelsDao.setFavorState(id = id, isFavorite = !channel.isFavorite)
    }

    override suspend fun getChannelById(id: Int): Channel {
        return channelsDao.getChannelById(id).toModel()
    }
}