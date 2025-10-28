package com.example.rickandmorty.presentation.character_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.domain.usecase.GetCharacterDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterDetailUiState())
    val uiState: StateFlow<CharacterDetailUiState> = _uiState.asStateFlow()

    init {
        val characterId = savedStateHandle.get<Int>("characterId")
        characterId?.let { id ->
            loadCharacter(id)
        } ?: run {
            _uiState.value = _uiState.value.copy(
                error = "Character ID not provided"
            )
        }
    }

    fun loadCharacter(characterId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val character = getCharacterDetailsUseCase(characterId)
                if (character != null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            character = character
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Character not found"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load character"
                    )
                }
            }
        }
    }
}

data class CharacterDetailUiState(
    val isLoading: Boolean = false,
    val character: com.example.rickandmorty.domain.model.Character? = null,
    val error: String? = null
)