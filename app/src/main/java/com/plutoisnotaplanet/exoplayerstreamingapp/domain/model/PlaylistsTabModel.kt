package com.plutoisnotaplanet.exoplayerstreamingapp.domain.model

data class PlaylistsTabModel(
    val all: List<ChannelShortModel>,
    val favorite: List<ChannelShortModel>,
    val shouldScrollToTop: Boolean
)