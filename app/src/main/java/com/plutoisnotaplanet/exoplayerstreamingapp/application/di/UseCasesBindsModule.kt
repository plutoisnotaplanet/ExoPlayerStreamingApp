package com.plutoisnotaplanet.exoplayerstreamingapp.application.di

import com.plutoisnotaplanet.exoplayerstreamingapp.domain.interactors.ChannelsInteractor
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.interactors.ExoPlayerInteractor
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.usecases.ChannelsUseCase
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.usecases.ExoPlayerUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCasesBindsModule {

    @Binds
    @ViewModelScoped
    fun bindChannelsInteractorToChannelsUseCase(channelsInteractor: ChannelsInteractor): ChannelsUseCase


    @Binds
    @ViewModelScoped
    fun bindExoPlayerInteractorToExoPlayerUseCase(exoPlayerInteractor: ExoPlayerInteractor): ExoPlayerUseCase

}