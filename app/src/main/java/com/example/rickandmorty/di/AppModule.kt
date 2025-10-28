package com.example.rickandmorty.di

import android.content.Context
import com.example.rickandmorty.data.local.RickAndMortyDatabase
import com.example.rickandmorty.data.remote.RetrofitInstance
import com.example.rickandmorty.data.remote.RickAndMortyApi
import com.example.rickandmorty.data.repository.CharacterRepositoryImpl
import com.example.rickandmorty.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRickAndMortyApi(): RickAndMortyApi {
        return RetrofitInstance.api
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RickAndMortyDatabase {
        return RickAndMortyDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(
        api: RickAndMortyApi,
        database: RickAndMortyDatabase
    ): CharacterRepository {
        return CharacterRepositoryImpl(api, database.characterDao())
    }
}