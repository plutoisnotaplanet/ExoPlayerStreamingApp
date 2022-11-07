package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter.view_holders

import androidx.recyclerview.widget.RecyclerView
import com.plutoisnotaplanet.exoplayerstreamingapp.databinding.ErrorViewHolderBinding
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter.ChannelAdapterItem

class ErrorViewHolder(
    private val binding: ErrorViewHolderBinding
): RecyclerView.ViewHolder(binding.root) {


    fun bind(item: ChannelAdapterItem) {
        (item as ChannelAdapterItem.ErrorItem).let {
            updateErrorMessage(it.message)
        }
    }

    fun updateErrorMessage(message: String) {
        binding.tvErrorMessage.text = message
    }
}