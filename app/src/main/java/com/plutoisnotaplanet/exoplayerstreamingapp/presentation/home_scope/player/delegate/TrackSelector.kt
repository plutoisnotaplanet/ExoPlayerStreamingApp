package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.delegate

import android.content.Context
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Tracks
import com.google.android.exoplayer2.trackselection.TrackSelectionOverride
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.PlayerViewModel
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.quality_dialog.QualityDialog
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.quality_dialog.QualityDialogAdapter
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.quality_dialog.QualityItem
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.quality_dialog.toQualityItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TrackSelector: TrackSelectorDelegate {

    override var qualityDialog: QualityDialog? = null
    override var qualityRv: RecyclerView? = null
    private var qualityDialogAdapter: QualityDialogAdapter? = null

    override fun selectVideoTrackWithList(
        context: Context,
        player: ExoPlayer?,
        viewModel: PlayerViewModel,
        recyclerView: RecyclerView
    ) {
        qualityRv = recyclerView
        val trackData = prepareTrackList(player, viewModel)

        if (qualityDialogAdapter == null) {
            qualityDialogAdapter = QualityDialogAdapter(context) { quality ->
                val qualityIndex = viewModel.getQualityMap()[quality]
                if (qualityIndex != null) {
                    setVideoTrack(player, trackData.second, qualityIndex)
                    viewModel.setSelectedQuality(quality)
                }
                viewModel.viewModelScope.launch {
                    delay(300)
                    recyclerView.isVisible = false
                }
            }
        }
        recyclerView.adapter = qualityDialogAdapter
        qualityDialogAdapter?.submitList(trackData.first)
        recyclerView.isVisible = !recyclerView.isVisible
    }

    override fun selectVideoTrackWithDialog(
        fragmentManager: FragmentManager,
        player: ExoPlayer?,
        viewModel: PlayerViewModel,
        hideSystemUi: () -> Unit
    ) {

        val trackData = prepareTrackList(player, viewModel)

        qualityDialog = QualityDialog.newInstance(trackData.first).apply {
            onSelectedQuality = { quality ->
                val qualityIndex = viewModel.getQualityMap()[quality]
                if (qualityIndex != null) {
                    setVideoTrack(player, trackData.second, qualityIndex)
                    viewModel.setSelectedQuality(quality)
                }
                hideSystemUi()
            }
        }
        qualityDialog?.show(fragmentManager, QualityDialog.TAG)
    }

    override fun hideQualityDialog() {
        qualityDialog?.dismiss()
    }

    private fun prepareTrackList(player: ExoPlayer?, viewModel: PlayerViewModel): TrackSelectorData {
        val tracks = player?.currentTracks
        var group: Tracks.Group? = null

        requireNotNull(tracks)

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
        return tempTracks to group
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

    override fun hideOrShowQualityList() {
        qualityRv?.isVisible = qualityRv?.isVisible != true
    }

    override fun nulifyProperties() {
        qualityRv = null
        qualityDialog = null
        qualityDialogAdapter = null
    }

}

typealias TrackSelectorData = Pair<List<QualityItem>, Tracks.Group?>