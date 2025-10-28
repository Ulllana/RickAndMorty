package com.example.rickandmorty.data.local.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.rickandmorty.data.local.RickAndMortyDatabase
import com.example.rickandmorty.data.local.entity.CharacterEntity
import com.example.rickandmorty.data.mapper.CharacterMapper
import com.example.rickandmorty.data.remote.RickAndMortyApi
import com.example.rickandmorty.data.remote.dto.CharactersResponse
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharactersRemoteMediator(
    private val database: RickAndMortyDatabase,
    private val api: RickAndMortyApi,
    private val name: String? = null,
    private val status: String? = null,
    private val species: String? = null,
    private val gender: String? = null
) : RemoteMediator<Int, CharacterEntity>() {

    private val characterDao = database.characterDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val response: CharactersResponse = api.getCharacters(
                page = loadKey,
                name = name,
                status = status,
                species = species,
                gender = gender
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    characterDao.clearAllCharacters()
                }
                characterDao.insertCharacters(CharacterMapper.listDtoToEntity(response.results))
            }

            MediatorResult.Success(
                endOfPaginationReached = response.info.next == null
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}