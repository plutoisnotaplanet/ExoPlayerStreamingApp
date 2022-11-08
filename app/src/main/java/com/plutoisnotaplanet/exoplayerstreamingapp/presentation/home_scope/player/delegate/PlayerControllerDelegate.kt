package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.delegate

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player

interface PlayerControllerDelegate {

    fun startPlayer(
        context: Context,
        exoPlayer: ExoPlayer,
        url: String?
    )

    fun stopPlayer(
        player: Player?
    )
}