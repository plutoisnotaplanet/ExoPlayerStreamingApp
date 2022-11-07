package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.quality_dialog

import androidx.recyclerview.widget.DiffUtil.ItemCallback

object QualityItemDiffCallback: ItemCallback<QualityItem>() {

    override fun areItemsTheSame(oldItem: QualityItem, newItem: QualityItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: QualityItem, newItem: QualityItem): Boolean {
        return oldItem.isSelected == newItem.isSelected
    }
}