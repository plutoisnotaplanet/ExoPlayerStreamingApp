package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.quality_dialog

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.plutoisnotaplanet.exoplayerstreamingapp.R
import com.plutoisnotaplanet.exoplayerstreamingapp.databinding.QualityViewHolderBinding

class QualityItemViewHolder(
    private val binding: QualityViewHolderBinding,
    private val onSelected: (Int) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onSelected(absoluteAdapterPosition)
        }
    }


    fun bind(item: QualityItem) {
        with(binding) {
            tvQuality.text = item.text
            if (item.isSelected) {
                llBackground.setBackgroundColor(root.context.getColor(R.color.selected_quality_color))
                tvQuality.setTextColor(root.context.getColor(R.color.white))
            } else {
                llBackground.setBackgroundColor(root.context.getColor(R.color.white))
                tvQuality.setTextColor(root.context.getColor(R.color.quality_text_color))
            }
            divider.isVisible = item != QualityItem.AUTO
        }
    }
}
