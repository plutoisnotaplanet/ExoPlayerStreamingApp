package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels

sealed class ChannelsAction {
    data class NavigateToExoPlayer(val channelId: Int): ChannelsAction()
}