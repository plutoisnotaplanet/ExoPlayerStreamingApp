package com.plutoisnotaplanet.exoplayerstreamingapp.application.di

import android.content.Context
import coil.ImageLoader
import com.plutoisnotaplanet.exoplayerstreamingapp.application.Constants
import com.plutoisnotaplanet.exoplayerstreamingapp.data.rest.Api
import com.plutoisnotaplanet.exoplayerstreamingapp.data.rest.ApiFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context
    ): ImageLoader {
        return ImageLoader.Builder(context)
            .okHttpClient { OkHttpClient() }
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(): Api =
        ApiFactory.create(
            Constants.ApiConstants.BASE_URL
        )
}