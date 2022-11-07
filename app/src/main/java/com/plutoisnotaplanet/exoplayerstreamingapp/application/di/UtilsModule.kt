package com.plutoisnotaplanet.exoplayerstreamingapp.application.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import coil.ImageLoader
import coil.imageLoader
import com.plutoisnotaplanet.exoplayerstreamingapp.BuildConfig
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
}