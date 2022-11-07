package com.plutoisnotaplanet.exoplayerstreamingapp.domain.usecases

import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.Response
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.PlayerViewState

interface ExoPlayerUseCase {

    suspend fun getChannelDataById(channelId: Int): Response<PlayerViewState>
}