package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels

import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.PlaylistsTabModel

sealed class ChannelsAction {
    data class NavigateToExoPlayer(val channelUrl: String): ChannelsAction()
}