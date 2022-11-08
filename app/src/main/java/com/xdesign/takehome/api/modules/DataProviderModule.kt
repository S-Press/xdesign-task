package com.xdesign.takehome.api.modules

import com.xdesign.takehome.api.CharacterAPI
import com.xdesign.takehome.data.repositories.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataProviderModule {

    @Provides
    @Singleton
    fun provideCharacterRepository(
        characterAPI: CharacterAPI
    ): CharacterRepository = CharacterRepository(
        characterAPI
    )
}