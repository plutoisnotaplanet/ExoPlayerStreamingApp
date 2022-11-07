package com.plutoisnotaplanet.exoplayerstreamingapp.data.repostiory

import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.Channel
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.ChannelShortModel
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface ChannelsRepository {

    suspend fun updatePlayList()

    suspend fun observePlayList(): Flow<Response<List<ChannelShortModel>>>

    suspend fun changeFavoriteStatus(id: Int)

    suspend fun getChannelById(id: Int): Channel
}