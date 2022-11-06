package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels

import androidx.lifecycle.viewModelScope
import com.plutoisnotaplanet.exoplayerstreamingapp.application.extensions.throttleLatest
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.Response
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.onFailure
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.onSuccess
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.usecases.ChannelsUseCase
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.common.BaseViewModel
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter.ChannelClickAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChannelsViewModel @Inject constructor(
    private val channelsUseCase: ChannelsUseCase
) : BaseViewModel<ChannelsAction, ChannelsViewState>() {

    val channelsFilter: MutableStateFlow<String> =
        MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    private val channelsFlow = channelsFilter
        .throttleLatest(300)
        .flatMapLatest { filter ->
            channelsUseCase.getChannelsWithFilter(filter.trim())
        }
        .flowOn(Dispatchers.IO)
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    private fun observeChannelsWithFilter() {
        viewModelScope.launchOnIoWithProgress {
            channelsFlow.collectLatest { response ->
                response
                    .onSuccess { channelViewState ->
                        _viewStateFlow.emit(Response.Success(channelViewState))
                    }
                    .onFailure {
                        _viewStateFlow.emit(Response.Error(it))
                    }
            }
        }
    }

    fun handleAction(action: ChannelClickAction) {
        when (action) {
            is ChannelClickAction.OnChannelFavoriteClick -> {
                changeFavorState(action.id)
            }
            is ChannelClickAction.OnChannelClick -> {
                setAction(ChannelsAction.NavigateToExoPlayer(action.url))
            }
        }
    }

    private fun changeFavorState(channelId: Int) {
        viewModelScope.launchOnIo {
            channelsUseCase.changeFavoriteStatus(channelId)
                .onSuccess { Timber.e("success") }
                .onFailure { Timber.e("error") }
        }
    }

    init {
        observeChannelsWithFilter()
    }
}