package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import com.plutoisnotaplanet.exoplayerstreamingapp.R
import com.plutoisnotaplanet.exoplayerstreamingapp.databinding.ChannelViewHolderBinding
import com.plutoisnotaplanet.exoplayerstreamingapp.databinding.ErrorViewHolderBinding
import com.plutoisnotaplanet.exoplayerstreamingapp.databinding.LoadingViewHolderBinding
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter.view_holders.ChannelTabErrorViewHolder
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter.view_holders.ChannelTabLoadingViewHolder
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter.view_holders.ChannelTabViewHolder

class ChannelsTabListAdapter(
    context: Context,
    private val imageLoader: ImageLoader?,
    private val onAction: (ChannelClickAction) -> Unit
): ListAdapter<ChannelAdapterItem, RecyclerView.ViewHolder>(ChannelTabDiffCallback) {

    companion object {
        const val NEW_CHANNEL_TITLE = "new_channel_title"
        const val NEW_CHANNEL_DESCRIPTION = "new_channel_description"
        const val NEW_CHANNEL_FAVOR_STATE = "new_channel_favor_state"
        const val NEW_ERROR_MESSAGE = "new_error_message"
    }

    override fun submitList(list: List<ChannelAdapterItem>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

    private val inflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            R.layout.channel_view_holder -> {
                ChannelTabViewHolder(
                    binding = ChannelViewHolderBinding.inflate(inflater, parent, false),
                    imageLoader = imageLoader,
                    onAction = onAction
                )
            }
            R.layout.loading_view_holder -> {
                ChannelTabLoadingViewHolder(
                    binding = LoadingViewHolderBinding.inflate(inflater, parent, false)
                )
            }
            R.layout.error_view_holder -> {
                ChannelTabErrorViewHolder(
                    binding = ErrorViewHolderBinding.inflate(inflater, parent, false),
                )
            }
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ChannelTabViewHolder -> holder.bind(getItem(holder.absoluteAdapterPosition))
            is ChannelTabErrorViewHolder -> holder.bind(getItem(holder.absoluteAdapterPosition))
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val item = getItem(holder.absoluteAdapterPosition)
            val payloadsFirst = payloads.firstOrNull()

            if (payloadsFirst is List<*>) {
                when(holder) {
                    is ChannelTabViewHolder -> {
                        val channel = (item as ChannelAdapterItem.ChannelItem).channel
                        payloadsFirst.forEach {
                            when (it) {
                                NEW_CHANNEL_TITLE -> holder.updateChannelName(channel.name)
                                NEW_CHANNEL_DESCRIPTION -> holder.updateChannelDescription(channel.description)
                                NEW_CHANNEL_FAVOR_STATE -> holder.updateFavorState(channel.isFavorite)
                            }
                        }
                    }
                    is ChannelTabErrorViewHolder -> {
                        val message = (item as ChannelAdapterItem.ErrorItem).message
                        payloadsFirst.forEach {
                            when (it) {
                                NEW_ERROR_MESSAGE -> holder.updateErrorMessage(message)
                            }
                        }
                    }
                }
            }
        }
    }
}