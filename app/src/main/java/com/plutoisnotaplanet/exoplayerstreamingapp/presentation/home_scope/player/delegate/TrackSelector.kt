package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.delegate

import androidx.fragment.app.FragmentManager
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Tracks
import com.google.android.exoplayer2.trackselection.TrackSelectionOverride
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.PlayerViewModel
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.quality_dialog.QualityDialog
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.quality_dialog.QualityItem
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.quality_dialog.toQualityItem

class TrackSelector: TrackSelectorDelegate {


    override fun selectVideoTrack(
        fragmentManager: FragmentManager,
        player: ExoPlayer?,
        viewModel: PlayerViewModel,
        hideSystemUi: () -> Unit
    ) {
        val tracks = player?.currentTracks ?: return
        var group: Tracks.Group? = null

        for (trackGroup in tracks.groups) {
            val trackType: Int = trackGroup.type
            if (trackType == C.TRACK_TYPE_VIDEO) {
                group = trackGroup
                for (i in 0 until trackGroup.length) {
                    val isSupported = trackGroup.isTrackSupported(i)
                    val trackFormat = trackGroup.getTrackFormat(i)

                    val height = trackFormat.height
                    when {
                        isSupported && height > 0 -> {
                            viewModel.addInQualityMap(height.toQualityItem(), i)
                        }
                    }
                }
            }
        }

        viewModel.addInQualityMap(QualityItem.AUTO, -1)

        val tempTracks = viewModel.getQualityMap().keys.sortedByDescending { it.positionInList }

        QualityDialog.newInstance(tempTracks).apply {
            onSelectedQuality = { quality ->
                val qualityIndex = viewModel.getQualityMap()[quality]
                if (qualityIndex != null) {
                    setVideoTrack(player, group, qualityIndex)
                    viewModel.setSelectedQuality(quality)
                }
                hideSystemUi()
            }
        }.show(fragmentManager, QualityDialog.TAG)
    }

    private fun setVideoTrack(
        player: ExoPlayer?,
        videoTrackGroup: Tracks.Group?,
        trackIndex: Int
    ) {
        when (trackIndex) {
            -1 -> {
                player?.trackSelectionParameters = player?.trackSelectionParameters!!
                    .buildUpon()
                    .setTrackTypeDisabled(C.TRACK_TYPE_VIDEO, false)
                    .setMaxVideoSizeSd()
                    .build()
            }
            else -> {
                player?.trackSelectionParameters = player?.trackSelectionParameters!!
                    .buildUpon()
                    .setTrackTypeDisabled(C.TRACK_TYPE_VIDEO, false)
                    .setOverrideForType(
                        TrackSelectionOverride(
                            videoTrackGroup?.mediaTrackGroup!!, trackIndex
                        )
                    )
                    .build()
            }
        }
    }

}