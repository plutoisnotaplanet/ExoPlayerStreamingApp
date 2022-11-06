package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter

sealed class ChannelClickAction {
    data class OnChannelClick(val url: String): ChannelClickAction()
    data class OnChannelFavoriteClick(val id: Int): ChannelClickAction()
}