package com.example.composesample.ui.feature.pokemondetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.composesample.model.PokemonDetail
import com.example.composesample.model.Sprites
import com.example.composesample.ui.common.LaunchedEffectAndCollect
import com.example.composesample.ui.common.ShowError
import org.koin.androidx.compose.getViewModel

@Composable
fun PokemonDetailScreen(
  viewModel: PokemonDetailViewModel = getViewModel(),
  pokemonId: Int,
  navController: NavHostController
) {
  val uiState by viewModel.viewState.collectAsStateWithLifecycle()

  LaunchedEffect(pokemonId) {
    viewModel.fetchPokemon(pokemonId)
  }

  uiState.uiData?.let { uiData -> PokemonDetail(uiData) { viewModel.setEvent(it) } }
  LoadingScreen(uiState.isLoading)
  ShowError(uiState.isError) { viewModel.setEvent(PokemonDetailContract.Event.Retry) }

  LaunchedEffectAndCollect(viewModel.effect) { effect ->
    when (effect) {
      is PokemonDetailContract.Effect.Navigation.Back -> navController.popBackStack()
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetail(pokemon: PokemonDetail, screenEvent: (PokemonDetailContract.Event) -> Unit) {
  Column(
    modifier = Modifier
      .fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    CenterAlignedTopAppBar(modifier = Modifier.padding(horizontal = 14.dp),
      title = {
        Text(text = "Pokemon Compose")
      },
      navigationIcon = {
        Icon(
          modifier = Modifier
            .size(16.dp)
            .clickable {
              screenEvent(PokemonDetailContract.Event.BackButtonClicked)
            }, imageVector = Icons.Filled.ArrowBack, contentDescription = null
        )
      })
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    )
    {
      Text(
        text = pokemon.name,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 8.dp)
      )
      Image(
        painter = rememberAsyncImagePainter(pokemon.sprites.front_default),
        contentDescription = null,
        modifier = Modifier.size(200.dp),
        contentScale = ContentScale.Crop
      )
      Text(
        text = "Height: ${pokemon.height / 10.0} m",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(vertical = 4.dp)
      )
      Text(
        text = "Weight: ${pokemon.weight / 10.0} kg",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(vertical = 4.dp)
      )
      Text(
        text = "Types:",
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 8.dp)
      )
      pokemon.types.forEach { typeSlot ->
        Text(
          text = typeSlot.type.name,
          style = MaterialTheme.typography.bodyLarge,
          color = Color.Gray,
          modifier = Modifier.padding(vertical = 2.dp)
        )
      }
    }
  }
}

@Composable
fun LoadingScreen(isLoading: Boolean) {
  AnimatedVisibility(visible = isLoading) {
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      Text("Loading...", style = MaterialTheme.typography.headlineSmall)
    }
  }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  PokemonDetail(pokemonDetailFake) {}
}

val pokemonDetailFake = PokemonDetail(
  id = 2019, name = "Macey", height = 2439, weight = 2654, sprites = Sprites(front_default = "Deana"), types = listOf()

)