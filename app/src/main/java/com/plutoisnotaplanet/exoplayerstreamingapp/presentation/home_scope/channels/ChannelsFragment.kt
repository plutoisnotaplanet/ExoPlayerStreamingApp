package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.plutoisnotaplanet.exoplayerstreamingapp.R
import com.plutoisnotaplanet.exoplayerstreamingapp.application.Constants
import com.plutoisnotaplanet.exoplayerstreamingapp.databinding.FragmentChannelsBinding
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.Response
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.common.BaseFragment
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.PlayerActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.update
import timber.log.Timber


@AndroidEntryPoint
class ChannelsFragment : BaseFragment<ChannelsAction, ChannelsViewState, ChannelsViewModel, FragmentChannelsBinding>() {

    override val viewModel: ChannelsViewModel by activityViewModels()

    override var _binding: FragmentChannelsBinding? = null

    override fun actionRender(action: ChannelsAction) {
        when(action) {
            is ChannelsAction.NavigateToExoPlayer -> {
                openPlayerActivity(action.channelId)
            }
            else -> {}
        }
    }

    override fun viewStateRender(state: Response<ChannelsViewState>) {
        when(state) {
            else -> {}
        }
    }

    private var channelsTabStateAdapter: ChannelsTabStateAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChannelsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        setupSearchViewListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        channelsTabStateAdapter = null
        _binding = null
    }

    private fun openPlayerActivity(channelId: Int) {
        val i = Intent(requireContext(), PlayerActivity::class.java)
        i.putExtra(Constants.Bundle.CHANNEL_ID, channelId)
        requireActivity().startActivity(i)
    }

    private fun setupSearchViewListener() {
        binding.svChannels.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.channelsFilter.update { newText ?: "" }
                return true
            }
        })
    }

    private fun setupViewPager() {
        channelsTabStateAdapter = ChannelsTabStateAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
        val viewPager = binding.vpChannels
        val tabLayout = binding.tlChannels
        viewPager.adapter = channelsTabStateAdapter
        viewPager.isSaveEnabled = false
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.tv_all)
                1 -> tab.text = getString(R.string.tv_favorites)
            }
        }.attach()
    }

}