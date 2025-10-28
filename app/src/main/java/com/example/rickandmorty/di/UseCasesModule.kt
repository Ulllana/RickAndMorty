package com.example.rickandmorty.di

import com.example.rickandmorty.domain.repository.CharacterRepository
import com.example.rickandmorty.domain.usecase.FilterCharactersUseCase
import com.example.rickandmorty.domain.usecase.GetCharacterDetailsUseCase
import com.example.rickandmorty.domain.usecase.GetCharactersUseCase
import com.example.rickandmorty.domain.usecase.RefreshCharactersUseCase
import com.example.rickandmorty.domain.usecase.SearchCharactersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    @Singleton
    fun provideGetCharactersUseCase(
        repository: CharacterRepository
    ): GetCharactersUseCase {
        return GetCharactersUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCharacterDetailsUseCase(
        repository: CharacterRepository
    ): GetCharacterDetailsUseCase {
        return GetCharacterDetailsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSearchCharactersUseCase(
        repository: CharacterRepository
    ): SearchCharactersUseCase {
        return SearchCharactersUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFilterCharactersUseCase(
        repository: CharacterRepository
    ): FilterCharactersUseCase {
        return FilterCharactersUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRefreshCharactersUseCase(
        repository: CharacterRepository
    ): RefreshCharactersUseCase {
        return RefreshCharactersUseCase(repository)
    }
}