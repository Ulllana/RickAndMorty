package com.example.rickandmorty.domain.repository

import androidx.paging.PagingData
import com.example.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharacters(): Flow<PagingData<Character>>

    suspend fun getCharacterById(id: Int): Character?

    fun searchCharacters(name: String): Flow<PagingData<Character>>

    fun filterCharacters(
        status: String? = null,
        species: String? = null,
        gender: String? = null
    ): Flow<PagingData<Character>>

    suspend fun refreshCharacters()
}