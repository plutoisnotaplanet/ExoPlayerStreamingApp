package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.delegate

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayer
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.PlayerViewModel
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.quality_dialog.QualityDialog
import kotlinx.coroutines.CoroutineScope

interface TrackSelectorDelegate {

    var qualityDialog: QualityDialog?
    var qualityRv: RecyclerView?

    fun selectVideoTrackWithDialog(
        fragmentManager: FragmentManager,
        player: ExoPlayer?,
        viewModel: PlayerViewModel,
        hideSystemUi: () -> Unit
    )

    fun selectVideoTrackWithList(
        context: Context,
        player: ExoPlayer?,
        viewModel: PlayerViewModel,
        recyclerView: RecyclerView
    )

    fun hideQualityDialog()

    fun hideOrShowQualityList()

    fun nulifyProperties()
}