package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter

import com.plutoisnotaplanet.exoplayerstreamingapp.R
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.ChannelShortModel

sealed class ChannelAdapterItem(val viewType: Int) {
    data class ChannelItem(val channel: ChannelShortModel) : ChannelAdapterItem(R.layout.channel_view_holder)
    object LoadingItem: ChannelAdapterItem(R.layout.loading_view_holder)
    data class ErrorItem(val message: String): ChannelAdapterItem(R.layout.error_view_holder)
}