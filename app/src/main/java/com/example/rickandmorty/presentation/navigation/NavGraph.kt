package com.example.rickandmorty.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.navArgument
import com.example.rickandmorty.presentation.character_detail.CharacterDetailScreen
import com.example.rickandmorty.presentation.characters.CharactersScreen

sealed class Screen(val route: String) {
    object Characters : Screen("characters")
    object CharacterDetail : Screen("character_detail/{characterId}") {
        fun createRoute(characterId: Int) = "character_detail/$characterId"
    }
}

fun NavGraphBuilder.appNavGraph(navController: NavController) {
    navigation(
        startDestination = Screen.Characters.route,
        route = "main"
    ) {
        composable(Screen.Characters.route) {
            CharactersScreen(
                onCharacterClick = { characterId ->
                    navController.navigate(Screen.CharacterDetail.createRoute(characterId))
                }
            )
        }
        composable(
            route = Screen.CharacterDetail.route,
            arguments = listOf(
                navArgument("characterId") {
                    type = androidx.navigation.NavType.IntType
                }
            )
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId")
            CharacterDetailScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}