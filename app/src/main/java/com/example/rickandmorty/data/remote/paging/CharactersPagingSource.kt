package com.example.rickandmorty.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.data.local.dao.CharacterDao
import com.example.rickandmorty.data.mapper.CharacterMapper
import com.example.rickandmorty.data.remote.RickAndMortyApi
import com.example.rickandmorty.data.remote.dto.CharactersResponse
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class CharactersPagingSource(
    private val api: RickAndMortyApi,
    private val characterDao: CharacterDao,
    private val name: String? = null,
    private val status: String? = null,
    private val species: String? = null,
    private val gender: String? = null
) : PagingSource<Int, com.example.rickandmorty.domain.model.Character>() {

    override fun getRefreshKey(state: PagingState<Int, com.example.rickandmorty.domain.model.Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, com.example.rickandmorty.domain.model.Character> {
        val page = params.key ?: 1

        return try {
            val response: CharactersResponse = api.getCharacters(
                page = page,
                name = name,
                status = status,
                species = species,
                gender = gender
            )

            val entities = response.results.map { dto ->
                CharacterMapper.dtoToEntity(dto)
            }
            characterDao.insertCharacters(entities)

            val characters = response.results.map { dto ->
                CharacterMapper.dtoToDomain(dto)
            }

            LoadResult.Page(
                data = characters,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.info.next == null) null else page + 1
            )
        } catch (exception: IOException) {
            loadAllFromDatabase()
        } catch (exception: HttpException) {
            if (exception.code() == 404) {
                loadAllFromDatabase()
            } else {
                loadAllFromDatabase()
            }
        } catch (exception: Exception) {
            loadAllFromDatabase()
        }
    }

    private suspend fun loadAllFromDatabase(): LoadResult<Int, com.example.rickandmorty.domain.model.Character> {
        return try {
            val charactersList = when {
                name != null -> {
                    characterDao.searchCharactersByName(name).first()
                }
                status != null || species != null || gender != null -> {
                    characterDao.getCharactersFiltered(status, species, gender).first()
                }
                else -> {
                    characterDao.getCharacters().first()
                }
            }

            val domainCharacters = charactersList.map { entity ->
                CharacterMapper.entityToDomain(entity)
            }

            LoadResult.Page(
                data = domainCharacters,
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}