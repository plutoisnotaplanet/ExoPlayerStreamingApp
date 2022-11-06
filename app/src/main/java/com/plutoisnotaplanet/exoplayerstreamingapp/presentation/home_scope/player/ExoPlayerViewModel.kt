package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player

import androidx.lifecycle.viewModelScope
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.onFailure
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.onSuccess
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.success
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.usecases.ExoPlayerUseCase
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.common.BaseViewModel
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.ChannelsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExoPlayerViewModel @Inject constructor(
    private val exoPlayerUseCase: ExoPlayerUseCase
): BaseViewModel<ExoPlayerAction, ExoPlayerViewState>() {

    val currentUrl: String = "https://cdn.jmvstream.com/w/LVW-8155/ngrp:LVW8155_41E1ciuCvO_all/playlist.m3u8"
    val testuri = "https://content.jwplatform.com/manifests/yp34SRmf.m3u8"

    val currentCdn = "http://limehd.cdnvideo.ru/streaming/spas/324/1/index.m3u8?md5=inUtnPA7p1Tn7momCNUifQ&e=1667575412"

    fun getExoPlayerUrlById(channelId: Int) {
        viewModelScope.launchOnIoWithProgress {
            exoPlayerUseCase.getExoPlayerUrlById(channelId)
                .onSuccess { _viewStateFlow.emit(success(ExoPlayerViewState.InitializePlayerWithUrl(it))) }
                .onFailure {  }
        }
    }
}