package com.example.rickandmorty.domain.usecase

import androidx.paging.PagingData
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilterCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(
        status: String? = null,
        species: String? = null,
        gender: String? = null
    ): Flow<PagingData<Character>> {
        return repository.filterCharacters(status, species, gender)
    }
}