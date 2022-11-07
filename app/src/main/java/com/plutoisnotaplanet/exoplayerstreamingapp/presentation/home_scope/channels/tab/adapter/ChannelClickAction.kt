package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter

sealed class ChannelClickAction {
    data class OnChannelClick(val id: Int): ChannelClickAction()
    data class OnChannelFavoriteClick(val id: Int): ChannelClickAction()
}