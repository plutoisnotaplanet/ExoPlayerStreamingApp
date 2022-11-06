package com.plutoisnotaplanet.exoplayerstreamingapp.application.di

import com.plutoisnotaplanet.exoplayerstreamingapp.data.repository_impl.ChannelsRepositoryImpl
import com.plutoisnotaplanet.exoplayerstreamingapp.data.repostiory.ChannelsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryBindsModule {

    @Binds
    fun bindChannelsRepositoryImplToChannelsRepository(channelsRepositoryImpl: ChannelsRepositoryImpl): ChannelsRepository

}