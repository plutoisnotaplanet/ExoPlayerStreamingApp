package com.plutoisnotaplanet.exoplayerstreamingapp.domain.interactors

import com.plutoisnotaplanet.exoplayerstreamingapp.data.repostiory.ChannelsRepository
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.Response
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.runResulting
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.usecases.ExoPlayerUseCase
import javax.inject.Inject

class ExoPlayerInteractor @Inject constructor(
    private val channelsRepository: ChannelsRepository
): ExoPlayerUseCase {


    override suspend fun getExoPlayerUrlById(channelId: Int): Response<String> {
        return runResulting {
            val channel = channelsRepository.getChannelById(channelId)
            channel.url ?: ""
        }
    }
}