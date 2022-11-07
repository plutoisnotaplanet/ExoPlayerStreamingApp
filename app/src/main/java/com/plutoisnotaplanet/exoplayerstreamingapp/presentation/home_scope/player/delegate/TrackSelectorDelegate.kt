package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.delegate

import androidx.fragment.app.FragmentManager
import com.google.android.exoplayer2.ExoPlayer
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.PlayerViewModel
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.quality_dialog.QualityDialog

interface TrackSelectorDelegate {

    var qualityDialog: QualityDialog?

    fun selectVideoTrack(
        fragmentManager: FragmentManager,
        player: ExoPlayer?,
        viewModel: PlayerViewModel,
        hideSystemUi: () -> Unit
    )

    fun hideQualityDialog()
}