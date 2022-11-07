package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.delegate

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player

interface PlayerControllerDelegate {

    fun initializePlayer(
        context: Context,
        url: String?
    ): ExoPlayer

    fun releasePlayer(
        player: Player?
    )
}