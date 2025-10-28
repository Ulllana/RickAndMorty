package com.example.rickandmorty.presentation.characters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.characters.components.CharacterCard
import com.example.rickandmorty.presentation.characters.components.FilterDialog
import com.example.rickandmorty.presentation.characters.components.SearchBar
import com.example.rickandmorty.presentation.characters.components.SwipeRefreshLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(
    onCharacterClick: (Int) -> Unit,
    viewModel: CharactersViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val charactersPagingData = viewModel.getPagingData().collectAsLazyPagingItems()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        uiState.error?.let { error ->
            val result = snackbarHostState.showSnackbar(
                message = error,
                actionLabel = "Retry",
                duration = SnackbarDuration.Long
            )
            if (result == SnackbarResult.ActionPerformed) {
                viewModel.refreshCharacters()
            }
            viewModel.clearError()
        }
    }

    if (uiState.showFilters) {
        FilterDialog(
            filterOptions = uiState.filterOptions,
            onApplyFilters = viewModel::applyFilters,
            onClearFilters = viewModel::clearFilters,
            onDismiss = { viewModel.showFilters(false) }
        )
    }

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
                actions = {
                    IconButton(onClick = { viewModel.showFilters(true) }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = stringResource(R.string.filters)
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SearchBar(
                query = uiState.searchQuery,
                onQueryChange = viewModel::searchCharacters,
                onSearch = { /* Handled by onQueryChange */ },
                modifier = Modifier.padding(vertical = 8.dp)
            )

            SwipeRefreshLayout(
                isRefreshing = uiState.isRefreshing,
                onRefresh = { viewModel.refreshCharacters() },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            count = charactersPagingData.itemCount,
                            key = { index ->
                                charactersPagingData.peek(index)?.id ?: index
                            }
                        ) { index ->
                            charactersPagingData[index]?.let { character ->
                                CharacterCard(
                                    character = character,
                                    onClick = { onCharacterClick(character.id) }
                                )
                            }
                        }
                    }
                    LaunchedEffect(uiState.isRefreshing) {
                        if (uiState.isRefreshing) {
                        } else {
                        }
                    }

                    if (charactersPagingData.loadState.refresh is androidx.paging.LoadState.Loading &&
                        charactersPagingData.itemCount == 0
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(48.dp),
                                    strokeWidth = 4.dp
                                )
                                Text(
                                    text = "Loading characters...",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }

                    if (charactersPagingData.loadState.refresh is androidx.paging.LoadState.NotLoading &&
                        charactersPagingData.itemCount == 0
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "No characters",
                                    modifier = Modifier.size(64.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = stringResource(R.string.no_characters_found),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                if (uiState.searchQuery.isNotBlank() || uiState.filterOptions.hasActiveFilters()) {
                                    Text(
                                        text = "Try changing your search or filters",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Button(
                                        onClick = {
                                            viewModel.clearFilters()
                                            viewModel.searchCharacters("")
                                        }
                                    ) {
                                        Text("Clear filters")
                                    }
                                }
                            }
                        }
                    }

                    if (charactersPagingData.loadState.append is androidx.paging.LoadState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(16.dp)
                        )
                    }

                    if (charactersPagingData.loadState.refresh is androidx.paging.LoadState.Error) {
                        val errorState = charactersPagingData.loadState.refresh as androidx.paging.LoadState.Error
                        LaunchedEffect(errorState) {
                            snackbarHostState.showSnackbar(
                                message = "Error loading characters: ${errorState.error.message}",
                                actionLabel = "Retry",
                                duration = SnackbarDuration.Long
                            )
                        }
                    }

                    if (uiState.isLoading && charactersPagingData.itemCount > 0) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}