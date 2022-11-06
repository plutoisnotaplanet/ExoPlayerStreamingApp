package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter.view_holders

import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.ImageLoader
import coil.request.ImageRequest
import coil.size.ViewSizeResolver
import com.plutoisnotaplanet.exoplayerstreamingapp.R
import com.plutoisnotaplanet.exoplayerstreamingapp.application.extensions.setSafeOnClickListener
import com.plutoisnotaplanet.exoplayerstreamingapp.databinding.ChannelViewHolderBinding
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.ChannelShortModel
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter.ChannelAdapterItem
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter.ChannelClickAction

class ChannelTabViewHolder(
    private val binding: ChannelViewHolderBinding,
    private val imageLoader: ImageLoader?,
    private val onAction: (ChannelClickAction) -> Unit
) : ViewHolder(binding.root) {

    init {
        with(binding) {
            ivChannelFavorite.setSafeOnClickListener {
                isChannelFavorite = !isChannelFavorite
                updateFavorState(isChannelFavorite)
                onAction(ChannelClickAction.OnChannelFavoriteClick(channelId))
            }
            clChannel.setSafeOnClickListener {
                onAction(ChannelClickAction.OnChannelClick(channelUrl))
            }
        }
    }

    private val context = binding.root.context
    private var channelId: Int = 0
    private var channelUrl: String = ""
    private var isChannelFavorite: Boolean = false

    fun bind(item: ChannelAdapterItem) {
        (item as ChannelAdapterItem.ChannelItem).let { item ->
            val channel = item.channel
            channelUrl = channel.url ?: ""
            channelId = channel.id
            isChannelFavorite = channel.isFavorite
            updateChannelName(channel.name)
            updateChannelDescription(channel.description)
            updateFavorState(channel.isFavorite)
            updateChannelLogo(channel.image)
        }
    }

    private fun updateChannelLogo(url: String?) {
        val request = ImageRequest.Builder(context)
            .data(url)
            .crossfade(true)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .size(ViewSizeResolver(binding.ivChannelLogo))
            .target(binding.ivChannelLogo)
            .build()
        imageLoader?.enqueue(request)
    }

    fun updateChannelName(newName: String?) {
        binding.tvChannelName.text = newName
    }

    fun updateChannelDescription(newDescription: String?) {
        binding.tvChannelDescription.text = newDescription
    }

    fun updateFavorState(isFavorite: Boolean) {
        with(binding) {
            if (isFavorite) {
                ivChannelFavorite.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.favorite_icon_active
                    )
                )
            } else {
                ivChannelFavorite.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.favorite_icon_inactive
                    )
                )
            }
        }
    }
}