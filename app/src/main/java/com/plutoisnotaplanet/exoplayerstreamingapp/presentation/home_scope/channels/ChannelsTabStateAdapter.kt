package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.ChannelsTabFragment
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.ChannelsTabState

class ChannelsTabStateAdapter(
    fragment: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragment, lifecycle) {

    companion object {
        private const val TABS_COUNT = 2
    }

    override fun getItemCount(): Int = TABS_COUNT

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> ChannelsTabFragment.newInstance(ChannelsTabState.ALL)
            1 -> ChannelsTabFragment.newInstance(ChannelsTabState.FAVORITES)
            else -> throw IllegalArgumentException()
        }
    }
}