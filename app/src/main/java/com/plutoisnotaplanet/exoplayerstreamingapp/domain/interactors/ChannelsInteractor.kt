package com.plutoisnotaplanet.exoplayerstreamingapp.domain.interactors

import com.plutoisnotaplanet.exoplayerstreamingapp.data.repostiory.ChannelsRepository
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.*
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.usecases.ChannelsUseCase
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.ChannelsViewState
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter.ChannelAdapterItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ChannelsInteractor @Inject constructor(
    private val channelsRepository: ChannelsRepository
) : ChannelsUseCase {

    override suspend fun observePlayListWithFilter(filter: String?): Flow<Response<ChannelsViewState>> =
        channelsRepository.observePlayList().flatMapLatest { response ->
            flow {
                response
                    .onSuccess { playList ->
                        val filteredPlayList = playList.filter { channel ->
                            if (!filter.isNullOrBlank()) {
                                channel.name?.contains(filter, true) ?: true
                            } else {
                                true
                            }
                        }.map { channelShortModel ->  ChannelAdapterItem.ChannelItem(channelShortModel)}

                        val allChannels = if (filteredPlayList.isNotEmpty()) {
                            filteredPlayList
                        } else {
                            listOf(ChannelAdapterItem.ErrorItem("No channels found"))
                        }

                        val favorite = filteredPlayList.filter { it.channel.isFavorite }

                        val favoriteChannels = if (favorite.isNotEmpty()) {
                            favorite
                        } else {
                            listOf(ChannelAdapterItem.ErrorItem("No favorite channels found"))
                        }

                        val channelsViewState = ChannelsViewState(
                            all = allChannels ,
                            favorite = favoriteChannels,
                            shouldScrollToTop = !filter.isNullOrBlank()
                        )

                        emit(success(channelsViewState))

                    }
                    .onFailure {
                        emit(error(it))
                    }
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun updatePlayList(): Response<Unit> {
        return runResulting {
            channelsRepository.updatePlayList()
        }
    }

    override suspend fun changeFavoriteStatus(id: Int): Response<Unit> {
        return runResulting {
            channelsRepository.changeFavoriteStatus(id)
        }
    }
}