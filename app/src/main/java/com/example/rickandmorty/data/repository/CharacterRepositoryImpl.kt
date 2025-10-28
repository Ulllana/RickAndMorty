package com.example.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.rickandmorty.data.local.dao.CharacterDao
import com.example.rickandmorty.data.remote.RickAndMortyApi
import com.example.rickandmorty.data.remote.paging.CharactersPagingSource
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi,
    private val characterDao: CharacterDao
) : CharacterRepository {

    override fun getCharacters(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                CharactersPagingSource(
                    api = api,
                    characterDao = characterDao
                )
            }
        ).flow
    }

    override suspend fun getCharacterById(id: Int): Character? {
        return try {
            val remoteCharacter = api.getCharacterById(id)
            val characterEntity = com.example.rickandmorty.data.mapper.CharacterMapper.dtoToEntity(remoteCharacter)
            characterDao.insertCharacter(characterEntity)
            com.example.rickandmorty.data.mapper.CharacterMapper.dtoToDomain(remoteCharacter)
        } catch (e: Exception) {
            val localCharacter = characterDao.getCharacterById(id)
            localCharacter?.let { com.example.rickandmorty.data.mapper.CharacterMapper.entityToDomain(it) }
        }
    }

    override fun searchCharacters(name: String): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CharactersPagingSource(
                    api = api,
                    characterDao = characterDao,
                    name = name
                )
            }
        ).flow
    }

    override fun filterCharacters(
        status: String?,
        species: String?,
        gender: String?
    ): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CharactersPagingSource(
                    api = api,
                    characterDao = characterDao,
                    status = status,
                    species = species,
                    gender = gender
                )
            }
        ).flow
    }

    override suspend fun refreshCharacters() {
        try {
            val response = api.getCharacters(page = 1)
            characterDao.clearAllCharacters()
            characterDao.insertCharacters(
                com.example.rickandmorty.data.mapper.CharacterMapper.listDtoToEntity(response.results)
            )
        } catch (e: Exception) {
            throw e
        }
    }
}