package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter.ChannelsTabListAdapter.Companion.NEW_CHANNEL_DESCRIPTION
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter.ChannelsTabListAdapter.Companion.NEW_CHANNEL_FAVOR_STATE
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter.ChannelsTabListAdapter.Companion.NEW_CHANNEL_TITLE
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter.ChannelsTabListAdapter.Companion.NEW_ERROR_MESSAGE

object ChannelTabDiffCallback : ItemCallback<ChannelAdapterItem>() {

    override fun areItemsTheSame(oldItem: ChannelAdapterItem, newItem: ChannelAdapterItem): Boolean {
        return when(oldItem) {
            is ChannelAdapterItem.ChannelItem -> {
                when(newItem) {
                    is ChannelAdapterItem.ChannelItem -> oldItem.channel.id == newItem.channel.id
                    else -> false
                }
            }
            is ChannelAdapterItem.ErrorItem -> {
                when(newItem) {
                    is ChannelAdapterItem.ErrorItem -> oldItem.message == newItem.message
                    else -> false
                }
            }
            else -> oldItem == newItem
        }
    }

    override fun areContentsTheSame(
        oldItem: ChannelAdapterItem,
        newItem: ChannelAdapterItem
    ): Boolean {
        return when(oldItem) {
            is ChannelAdapterItem.ChannelItem -> {
                when(newItem) {
                    is ChannelAdapterItem.ChannelItem -> {
                        oldItem.channel.name == newItem.channel.name &&
                                oldItem.channel.description == newItem.channel.description &&
                                oldItem.channel.isFavorite == newItem.channel.isFavorite
                    }
                    else -> false
                }
            }
            is ChannelAdapterItem.ErrorItem -> {
                when(newItem) {
                    is ChannelAdapterItem.ErrorItem -> oldItem.message == newItem.message
                    else -> false
                }
            }
            else -> oldItem == newItem
        }
    }

    override fun getChangePayload(oldItem: ChannelAdapterItem, newItem: ChannelAdapterItem): Any? {
        val diffList = mutableListOf<String>()
        return when(oldItem) {
            is ChannelAdapterItem.ChannelItem -> {
                when(newItem) {
                    is ChannelAdapterItem.ChannelItem -> {
                        if (oldItem.channel.name == newItem.channel.name &&
                            oldItem.channel.description == newItem.channel.description &&
                            oldItem.channel.isFavorite == newItem.channel.isFavorite) {
                            super.getChangePayload(oldItem, newItem)
                        } else {
                            if (oldItem.channel.name != newItem.channel.name) diffList.add(NEW_CHANNEL_TITLE)
                            if (oldItem.channel.description != newItem.channel.description) diffList.add(NEW_CHANNEL_DESCRIPTION)
                            if (oldItem.channel.isFavorite != newItem.channel.isFavorite) diffList.add(NEW_CHANNEL_FAVOR_STATE)
                            diffList
                        }
                    }
                    else -> super.getChangePayload(oldItem, newItem)
                }
            }
            is ChannelAdapterItem.ErrorItem -> {
                when(newItem) {
                    is ChannelAdapterItem.ErrorItem -> {
                        if (oldItem.message == newItem.message) {
                            super.getChangePayload(oldItem, newItem)
                        } else {
                            diffList.add(NEW_ERROR_MESSAGE)
                            diffList
                        }
                    }
                    else -> super.getChangePayload(oldItem, newItem)
                }
            }
            else -> super.getChangePayload(oldItem, newItem)
        }
    }
}