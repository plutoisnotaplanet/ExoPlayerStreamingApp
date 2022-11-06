package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.ImageLoader
import coil.imageLoader
import com.plutoisnotaplanet.exoplayerstreamingapp.databinding.FragmentChannelsTabBinding
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.Response
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.common.BaseFragment
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.ChannelsViewModel
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.ChannelsAction
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.ChannelsViewState
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter.ChannelAdapterItem
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter.ChannelsTabItemDecorator
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.channels.tab.adapter.ChannelsTabListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber


class ChannelsTabFragment :
    BaseFragment<ChannelsAction, ChannelsViewState, ChannelsViewModel, FragmentChannelsTabBinding>() {

    companion object {
        private const val CHANNELS_TAB_STATE = "CHANNELS_TAB_STATE"

        @JvmStatic
        fun newInstance(state: ChannelsTabState) =
            ChannelsTabFragment().apply {
                arguments = Bundle().apply {
                    putInt(CHANNELS_TAB_STATE, state.ordinal)
                }
            }
    }

    override val viewModel: ChannelsViewModel by activityViewModels()

    override var _binding: FragmentChannelsTabBinding? = null

    override fun actionRender(action: ChannelsAction) {
        when (action) {
            else -> {}
        }
    }

    override fun viewStateRender(state: Response<ChannelsViewState>) {
        when(state) {
            is Response.Loading -> {
                channelsTabAdapter?.submitList(listOf(ChannelAdapterItem.LoadingItem))
            }
            is Response.Success -> {
                val playListTabModel = state.data
                when(tabState) {
                    ChannelsTabState.ALL.ordinal -> {
                        channelsTabAdapter?.submitList(playListTabModel.all) {
                            if (playListTabModel.shouldScrollToTop) {
                                binding.rvChannels.scrollToPosition(0)
                            }
                        }
                    }
                    ChannelsTabState.FAVORITES.ordinal -> {
                        channelsTabAdapter?.submitList(playListTabModel.favorite) {
                            if (playListTabModel.shouldScrollToTop) {
                                binding.rvChannels.scrollToPosition(0)
                            }
                        }
                    }
                    else -> {}
                }
            }
            is Response.Error -> {
                channelsTabAdapter?.submitList(listOf(ChannelAdapterItem.ErrorItem(state.error.message ?: "Error")))
            }
        }
    }

    private val tabState: Int? by lazy { arguments?.getInt(CHANNELS_TAB_STATE) }
    private var channelsTabAdapter: ChannelsTabListAdapter? = null
    private var imageLoader: ImageLoader? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChannelsTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageLoader = requireContext().applicationContext.imageLoader
        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        channelsTabAdapter = null
        imageLoader = null
        _binding = null
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rvChannels
        channelsTabAdapter = ChannelsTabListAdapter(requireContext(), imageLoader) { action ->
            viewModel.handleAction(action)
        }
        recyclerView.adapter = channelsTabAdapter
        recyclerView.addItemDecoration(ChannelsTabItemDecorator(requireContext()))
    }


}