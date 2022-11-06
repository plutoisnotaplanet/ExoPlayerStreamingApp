package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.plutoisnotaplanet.exoplayerstreamingapp.application.extensions.convertIntToDp

class ChannelsTabItemDecorator(context: Context) : RecyclerView.ItemDecoration() {

    private val twelveDp = context.convertIntToDp(12)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).absoluteAdapterPosition
        when (itemPosition) {
            0 -> {
                outRect.top = twelveDp
            }
            state.itemCount - 1 -> {
                outRect.bottom = twelveDp
            }
        }
    }
}