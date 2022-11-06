package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.source.hls.DefaultHlsDataSourceFactory
import com.google.android.exoplayer2.source.hls.HlsDataSourceFactory
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistParserFactory
import com.google.android.exoplayer2.source.hls.playlist.FilteringHlsPlaylistParserFactory
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParserFactory
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DataSchemeDataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.util.Util
import com.plutoisnotaplanet.exoplayerstreamingapp.R
import com.plutoisnotaplanet.exoplayerstreamingapp.application.Constants
import com.plutoisnotaplanet.exoplayerstreamingapp.databinding.FragmentExoPlayerBinding
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.Response
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.common.BaseFragment


class ExoPlayerFragment : BaseFragment<ExoPlayerAction, ExoPlayerViewState, ExoPlayerViewModel, FragmentExoPlayerBinding>() {

    override val viewModel: ExoPlayerViewModel by activityViewModels()
    override var _binding: FragmentExoPlayerBinding? = null

    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L

    private val channelId: Int by lazy { requireArguments().getInt(Constants.Bundle.CHANNEL_ID) }
    private val channelUrl: String? by lazy { requireArguments().getString(Constants.Bundle.CHANNEL_URL) }

    override fun actionRender(action: ExoPlayerAction) {
        when(action) {
            else -> {}
        }
    }

    override fun viewStateRender(state: Response<ExoPlayerViewState>) {
        when(state) {
            is Response.Loading -> {}
            is Response.Success -> {
                if (state.data is ExoPlayerViewState.InitializePlayerWithUrl) {
//                    initializePlayer(state.data.url)
                }
            }
            is Response.Error -> {}
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExoPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }


    private fun initializePlayer(url: String? = null) {
        val bandWidthMeter = DefaultBandwidthMeter.Builder(requireContext()).build()
        val trackSelectorFactory = AdaptiveTrackSelection.Factory()
        val trackSelector = DefaultTrackSelector(requireContext(), trackSelectorFactory)
        val loadControl = DefaultLoadControl()
        player = ExoPlayer.Builder(requireContext())
            .setBandwidthMeter(bandWidthMeter)
            .setTrackSelector(trackSelector)
            .setLoadControl(loadControl)
            .build()
            .also { exoPlayer ->
                binding.videoView.player = exoPlayer

                exoPlayer.setMediaSource(buildHlsMediaSource())
                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.seekTo(currentItem, playbackPosition)
                exoPlayer.prepare()
            }
    }

    private fun buildHlsMediaSource(): MediaSource {
        val item = MediaItem.Builder()
            .setMimeType(MimeTypes.APPLICATION_M3U8) // optional
            .setUri(viewModel.currentUrl)

        return HlsMediaSource.Factory(DefaultHlsDataSourceFactory(DefaultHttpDataSource.Factory()))
            .setPlaylistParserFactory(DefaultHlsPlaylistParserFactory()) // This is your implementation
            .createMediaSource(item.build())
    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentItem = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
        player = null
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        WindowInsetsControllerCompat(requireActivity().window, binding.videoView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

}