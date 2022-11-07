package com.plutoisnotaplanet.exoplayerstreamingapp.domain.usecases

import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.Response
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.ChannelsViewState
import kotlinx.coroutines.flow.Flow

interface ChannelsUseCase {

    suspend fun observePlayListWithFilter(filter: String? = null): Flow<Response<ChannelsViewState>>

    suspend fun updatePlayList(): Response<Unit>

    suspend fun changeFavoriteStatus(id: Int): Response<Unit>
}