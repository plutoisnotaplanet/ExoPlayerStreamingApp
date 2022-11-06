package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels

import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter.ChannelAdapterItem

data class ChannelsViewState(
    val all: List<ChannelAdapterItem>,
    val favorite: List<ChannelAdapterItem>,
    val shouldScrollToTop: Boolean
)