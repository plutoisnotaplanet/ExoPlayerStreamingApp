package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.quality_dialog

import android.graphics.ColorFilter
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.plutoisnotaplanet.exoplayerstreamingapp.R
import com.plutoisnotaplanet.exoplayerstreamingapp.databinding.QualityViewHolderBinding

class QualityItemViewHolder(
    private val binding: QualityViewHolderBinding,
    private val isLastInList: (Int) -> Boolean,
    private val onSelected: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onSelected(absoluteAdapterPosition)
        }
    }

    inline val isFirstInList: Boolean
        get() = absoluteAdapterPosition == 0

    fun bind(item: QualityItem) {
        with(binding) {
            tvQuality.text = item.text
            val textColor = if (item.isSelected) {
                R.color.white
            } else R.color.quality_text_color
            tvQuality.setTextColor(AppCompatResources.getColorStateList(root.context, textColor))
            when {
                isFirstInList -> {
                    val bgDrawable = if (item.isSelected) {
                        R.drawable.selected_top_rounded_corners
                    } else {
                        R.drawable.top_rounded_corners
                    }
                    llBackground.setBackgroundResource(bgDrawable)
                }
                isLastInList(absoluteAdapterPosition) -> {
                    val bgDrawable: Int
                     if (item.isSelected) {
                        bgDrawable = R.drawable.selected_bottom_rounded_corners
                    } else {
                        bgDrawable = R.drawable.bottom_rounded_corners
                    }
                    llBackground.setBackgroundResource(bgDrawable)
                }
                else -> {
                    val bgColor = if (item.isSelected) {
                        R.color.selected_quality_color
                    } else {
                        R.color.white
                    }
                    llBackground.setBackgroundColor(AppCompatResources.getColorStateList(root.context, bgColor).defaultColor)
                }
            }
            divider.isVisible = item != QualityItem.AUTO
        }
    }
}
