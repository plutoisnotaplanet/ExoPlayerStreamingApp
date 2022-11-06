package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player

sealed class ExoPlayerViewState {
    data class InitializePlayerWithUrl(val url: String) : ExoPlayerViewState()
}