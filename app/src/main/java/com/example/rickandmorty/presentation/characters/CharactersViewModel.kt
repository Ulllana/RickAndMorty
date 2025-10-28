package com.example.rickandmorty.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.domain.model.FilterOptions
import com.example.rickandmorty.domain.usecase.FilterCharactersUseCase
import com.example.rickandmorty.domain.usecase.GetCharactersUseCase
import com.example.rickandmorty.domain.usecase.RefreshCharactersUseCase
import com.example.rickandmorty.domain.usecase.SearchCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val searchCharactersUseCase: SearchCharactersUseCase,
    private val filterCharactersUseCase: FilterCharactersUseCase,
    private val refreshCharactersUseCase: RefreshCharactersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharactersUiState())
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()

    private var refreshTrigger = MutableStateFlow(0)

    private var currentPagingData: Flow<PagingData<Character>> = getCharactersUseCase()
        .cachedIn(viewModelScope)

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        currentPagingData = getCharactersUseCase()
            .cachedIn(viewModelScope)
        _uiState.update { it.copy(isLoading = false) }
    }

    fun searchCharacters(query: String) {
        _uiState.update {
            it.copy(
                searchQuery = query,
                isLoading = true,
                error = null
            )
        }

        if (query.isBlank()) {
            currentPagingData = getCharactersUseCase()
                .cachedIn(viewModelScope)
        } else {
            currentPagingData = searchCharactersUseCase(query)
                .cachedIn(viewModelScope)
        }
        _uiState.update { it.copy(isLoading = false) }
    }

    fun applyFilters(filterOptions: FilterOptions) {
        _uiState.update {
            it.copy(
                filterOptions = filterOptions,
                isLoading = true,
                error = null,
                showFilters = false
            )
        }

        currentPagingData = filterCharactersUseCase(
            status = filterOptions.status,
            species = filterOptions.species,
            gender = filterOptions.gender
        ).cachedIn(viewModelScope)
        _uiState.update { it.copy(isLoading = false) }
    }

    fun clearFilters() {
        _uiState.update {
            it.copy(
                filterOptions = FilterOptions(),
                isLoading = true,
                error = null
            )
        }
        currentPagingData = getCharactersUseCase()
            .cachedIn(viewModelScope)
        _uiState.update { it.copy(isLoading = false) }
    }

    fun refreshCharacters() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true, error = null) }
            try {
                refreshCharactersUseCase()

                refreshTrigger.value++
                when {
                    uiState.value.searchQuery.isNotBlank() -> {
                        currentPagingData = searchCharactersUseCase(uiState.value.searchQuery)
                            .cachedIn(viewModelScope)
                    }
                    uiState.value.filterOptions.hasActiveFilters() -> {
                        currentPagingData = filterCharactersUseCase(
                            status = uiState.value.filterOptions.status,
                            species = uiState.value.filterOptions.species,
                            gender = uiState.value.filterOptions.gender
                        ).cachedIn(viewModelScope)
                    }
                    else -> {
                        currentPagingData = getCharactersUseCase()
                            .cachedIn(viewModelScope)
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isRefreshing = false,
                        error = "No internet connection. Showing cached data."
                    )
                }
                viewModelScope.launch {
                    kotlinx.coroutines.delay(2000)
                    _uiState.update { it.copy(error = null) }
                }
            } finally {
                _uiState.update { it.copy(isRefreshing = false) }
            }
        }
    }

    fun showFilters(show: Boolean) {
        _uiState.update { it.copy(showFilters = show) }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    fun getPagingData(): Flow<PagingData<Character>> {
        return currentPagingData
    }
}

data class CharactersUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val filterOptions: FilterOptions = FilterOptions(),
    val error: String? = null,
    val showFilters: Boolean = false
)