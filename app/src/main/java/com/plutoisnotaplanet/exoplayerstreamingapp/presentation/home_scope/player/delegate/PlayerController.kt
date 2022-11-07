package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.delegate

import android.content.Context
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.ts.DefaultTsPayloadReaderFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.DefaultHlsDataSourceFactory
import com.google.android.exoplayer2.source.hls.DefaultHlsExtractorFactory
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistParserFactory
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.util.MimeTypes

class PlayerController: PlayerControllerDelegate {

    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L

    override fun initializePlayer(
        context: Context,
        url: String?
    ): ExoPlayer {
        val bandWidthMeter = DefaultBandwidthMeter.Builder(context).build()
        val trackSelectorFactory = AdaptiveTrackSelection.Factory()
        val trackSelector = DefaultTrackSelector(context, trackSelectorFactory)
        val rendererFactory = DefaultRenderersFactory(context).setEnableDecoderFallback(true)
        val loadControl = DefaultLoadControl.Builder()
            .setBufferDurationsMs(60000, 60000, 2500, 2500).build()
        return ExoPlayer.Builder(context)
            .setTrackSelector(trackSelector)
            .setBandwidthMeter(bandWidthMeter)
            .setRenderersFactory(rendererFactory)
            .setLoadControl(loadControl)
            .build()
            .also { exoPlayer ->
                exoPlayer.setMediaSource(buildHlsMediaSource(context,url))
                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.seekTo(currentItem, playbackPosition)
                exoPlayer.prepare()
            }
    }

    private fun buildHlsMediaSource(context: Context, url: String?): MediaSource {
        val item = MediaItem.Builder()
            .setMimeType(MimeTypes.APPLICATION_M3U8)
            .setUri(url)

        val extractorFactory = DefaultHlsExtractorFactory(DefaultTsPayloadReaderFactory.FLAG_ALLOW_NON_IDR_KEYFRAMES, true)
        val hlsPlayListFactory = DefaultHlsPlaylistParserFactory()

        return HlsMediaSource.Factory(DefaultHlsDataSourceFactory(DefaultDataSource.Factory(context)))
            .setPlaylistParserFactory(hlsPlayListFactory)
            .setExtractorFactory(extractorFactory)
            .createMediaSource(item.build())
    }

    override fun releasePlayer(
        player: Player?,
    ) {
        player?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentItem = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
    }
}