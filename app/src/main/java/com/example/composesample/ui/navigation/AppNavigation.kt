package com.example.composesample.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composesample.model.PokemonDetail
import com.example.composesample.ui.feature.pokemondetail.PokemonDetailScreen
import com.example.composesample.ui.feature.pokemonlist.PokemonListScreen
import com.example.composesample.ui.navigation.Navigation.Args.POKEMON_ID

@Composable
fun AppNavigation() {

  val navController = rememberNavController()

  NavHost(
    navController = navController,
    startDestination = Navigation.Routes.POKEMON_LIST
  ) {
    composable(
      route = Navigation.Routes.POKEMON_LIST
    ) {
      PokemonListScreen(navController = navController)
    }

    composable(
        route = Navigation.Routes.POKEMON_DETAIL,
        arguments = listOf(navArgument(name = POKEMON_ID) {
            type = NavType.IntType
        })
    ) { backStackEntry ->
        val userId = requireNotNull(backStackEntry.arguments?.getInt(POKEMON_ID)) { "User id is required as an argument" }
        PokemonDetailScreen(
          pokemonId = userId,
          navController = navController
        )
    }


  }
}

object Navigation {

  object Args {
    const val POKEMON_ID = "pokemon_id"
  }

  object Routes {
    const val POKEMON_LIST = "pokemon_list"
    const val POKEMON_DETAIL = "$POKEMON_LIST/{$POKEMON_ID}"
  }

}

fun NavController.navigateToDetail(userId: Int) {
  navigate(route = "${Navigation.Routes.POKEMON_LIST}/$userId")
}
