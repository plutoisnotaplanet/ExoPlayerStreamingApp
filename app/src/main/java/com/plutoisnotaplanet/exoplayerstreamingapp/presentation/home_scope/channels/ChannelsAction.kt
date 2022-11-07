package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels

sealed class ChannelsAction {
    data class OpenPlayerActivity(val channelId: Int): ChannelsAction()
}