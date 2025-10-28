package com.example.rickandmorty.domain.usecase

import androidx.paging.PagingData
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(name: String): Flow<PagingData<Character>> {
        return repository.searchCharacters(name)
    }
}