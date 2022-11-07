package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.delegate

import androidx.fragment.app.FragmentManager
import com.google.android.exoplayer2.ExoPlayer
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.PlayerViewModel

interface TrackSelectorDelegate {

    fun selectVideoTrack(
        fragmentManager: FragmentManager,
        player: ExoPlayer?,
        viewModel: PlayerViewModel,
        hideSystemUi: () -> Unit
    )

}