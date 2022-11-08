package com.plutoisnotaplanet.exoplayerstreamingapp.application

object Constants {

    object ApiConstants {
        const val TIMEOUT = 15L
        const val BASE_URL = "https://limehd.online/"
    }

    object Bundle {
        const val CHANNEL_ID = "channel_id"
    }

    object View {
        const val CHANNEL_IV_SIZE = 44
    }

    object ExoPlayer {
        const val MIN_BUFFER_MS = 5000
        const val MAX_BUFFER_MS = 10000
        const val BUFFER_FOR_PLAYBACK_MS = 2500
        const val BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS = 5000
    }
}