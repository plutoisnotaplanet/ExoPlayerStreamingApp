package com.plutoisnotaplanet.exoplayerstreamingapp.application.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import coil.ImageLoader
import coil.imageLoader
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.plutoisnotaplanet.exoplayerstreamingapp.BuildConfig
import com.plutoisnotaplanet.exoplayerstreamingapp.application.Constants
import com.plutoisnotaplanet.exoplayerstreamingapp.data.db.ExoPlayerStreamingDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): ExoPlayerStreamingDataBase {
        return Room.databaseBuilder(
            context,
            ExoPlayerStreamingDataBase::class.java,
            BuildConfig.APPLICATION_ID
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideExoPlayer(@ApplicationContext context: Context): ExoPlayer {
        val bandWidthMeter = DefaultBandwidthMeter.Builder(context).build()
        val trackSelectorFactory = AdaptiveTrackSelection.Factory()
        val trackSelector = DefaultTrackSelector(context, trackSelectorFactory)
        val rendererFactory = DefaultRenderersFactory(context).setEnableDecoderFallback(true)
        val loadControl = DefaultLoadControl.Builder()
            .setBufferDurationsMs(Constants.ExoPlayer.MIN_BUFFER_MS, Constants.ExoPlayer.MAX_BUFFER_MS, Constants.ExoPlayer.BUFFER_FOR_PLAYBACK_MS, Constants.ExoPlayer.BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS).build()
        return ExoPlayer.Builder(context)
            .setTrackSelector(trackSelector)
            .setBandwidthMeter(bandWidthMeter)
            .setRenderersFactory(rendererFactory)
            .setLoadControl(loadControl)
            .build()
    }
}