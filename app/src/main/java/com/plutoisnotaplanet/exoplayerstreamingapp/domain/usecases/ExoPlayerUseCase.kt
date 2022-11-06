package com.plutoisnotaplanet.exoplayerstreamingapp.domain.usecases

import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.Response

interface ExoPlayerUseCase {

    suspend fun getExoPlayerUrlById(channelId: Int): Response<String>
}