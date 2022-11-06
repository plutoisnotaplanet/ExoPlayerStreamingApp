package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.plutoisnotaplanet.exoplayerstreamingapp.R
import com.plutoisnotaplanet.exoplayerstreamingapp.application.Constants
import com.plutoisnotaplanet.exoplayerstreamingapp.databinding.FragmentChannelsBinding
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.Response
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.update


@AndroidEntryPoint
class ChannelsFragment : BaseFragment<ChannelsAction, ChannelsViewState, ChannelsViewModel, FragmentChannelsBinding>() {

    override val viewModel: ChannelsViewModel by activityViewModels()

    override var _binding: FragmentChannelsBinding? = null

    override fun actionRender(action: ChannelsAction) {
        when(action) {
            is ChannelsAction.NavigateToExoPlayer -> {
                findNavController().navigate(R.id.ExoPlayerFragment, bundleOf(Constants.Bundle.CHANNEL_URL to action.channelUrl))
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