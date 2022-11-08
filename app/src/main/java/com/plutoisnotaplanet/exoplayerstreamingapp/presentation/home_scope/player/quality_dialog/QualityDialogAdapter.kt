package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.quality_dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.plutoisnotaplanet.exoplayerstreamingapp.databinding.QualityViewHolderBinding

class QualityDialogAdapter(
    context: Context,
    private val onSelected: (QualityItem) -> Unit
): ListAdapter<QualityItem, QualityItemViewHolder>(QualityItemDiffCallback) {


    private val inflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QualityItemViewHolder {
        return QualityItemViewHolder(QualityViewHolderBinding.inflate(inflater, parent, false), ::isLastInList) {
            currentList.forEach { item ->
                item.isSelected = false
            }
            currentList[it].isSelected = true
            notifyDataSetChanged()
            onSelected(currentList[it])
        }
    }

    override fun onBindViewHolder(holder: QualityItemViewHolder, position: Int) {
        holder.bind(getItem(holder.absoluteAdapterPosition))
    }

    private fun isLastInList(position: Int): Boolean {
        return position == currentList.size - 1
    }

}