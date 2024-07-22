package com.example.composesample.ui.feature.pokemonlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.composesample.model.Pokemon
import com.example.composesample.ui.common.LaunchedEffectAndCollect
import com.example.composesample.ui.common.ShowError
import com.example.composesample.ui.common.ShowLoader
import com.example.composesample.ui.navigation.navigateToDetail
import org.koin.androidx.compose.getViewModel

@Composable
fun PokemonListScreen(
  viewModel: PokemonListViewModel = getViewModel(),
  navController: NavHostController
) {
  val uiState by viewModel.viewState.collectAsStateWithLifecycle()
  PokemonList(uiState) { event -> viewModel.setEvent(event) }

  LaunchedEffectAndCollect(viewModel.effect) { effect ->
    when (effect) {
      is PokemonListContract.Effect.Navigation.Back -> navController.popBackStack()
      is PokemonListContract.Effect.Navigation.Detail -> navController.navigateToDetail(effect.id)
    }
  }
}

@Composable
private fun PokemonList(
  uiState: PokemonListContract.UiState,
  screenEvent: (PokemonListContract.Event) -> Unit
) {
  ChaptersList(uiState.uiData, screenEvent)
  OrderFloatingButton(uiState.isOrderDescend, screenEvent)
  ShowLoader(uiState.isLoading)
  ShowError(uiState.isError) { screenEvent(PokemonListContract.Event.Retry) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChaptersList(data: List<Pokemon>, screenEvent: (PokemonListContract.Event) -> Unit) {
  Column {
    CenterAlignedTopAppBar(modifier = Modifier.padding(horizontal = 14.dp),
      title = {
      Text(text = "Pokemon Compose")
    },
      navigationIcon = {
        Icon(
          modifier = Modifier
            .size(16.dp)
            .clickable {
              screenEvent(PokemonListContract.Event.BackButtonClicked)
            }, imageVector = Icons.Filled.ArrowBack, contentDescription = null
        )
      })

    LazyVerticalStaggeredGrid(modifier = Modifier.fillMaxWidth(),
      columns = StaggeredGridCells.Fixed(2),
      contentPadding = PaddingValues(10.dp, 10.dp, 10.dp, 64.dp),
      content = {
        items(count = data.size) { index ->
          PokemonItem(data[index]) { _ ->
            screenEvent(PokemonListContract.Event.OnPokemonClicked(data[index].id))
          }
        }
      })
  }
}

@Composable
private fun PokemonItem(item: Pokemon, onItemClick: (Pokemon) -> Unit) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(10.dp)
      .clickable { onItemClick(item) },
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {

    AsyncImage(
      model = item.imageUrl,
      contentScale = ContentScale.FillHeight,
      modifier = Modifier
        .fillMaxWidth()
        .height(100.dp),
      contentDescription = null,
    )

    Column(
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = item.name.uppercase(),
        textAlign = TextAlign.Center
      )
    }
  }
}

@Composable
fun OrderFloatingButton(isOrderDescend: Boolean, screenEvent: (PokemonListContract.Event) -> Unit) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .padding(20.dp),
    contentAlignment = Alignment.BottomEnd
  ) {
    ExtendedFloatingActionButton(
      onClick = {
        screenEvent(PokemonListContract.Event.OrderButtonClicked)
      },
      icon = {
        Icon(
          imageVector = if (isOrderDescend) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
          contentDescription = null
        )
      },
      text = { Text(text = "Ordenar Lista") },
    )
  }
}

@Preview
@Composable
private fun OrderFloatingButtonPreview() {
  OrderFloatingButton(true) {}
}

@Preview(showBackground = true, widthDp = 350)
@Composable
private fun MassimoFeelJournalScreenViewPreview() {
  ChaptersList(fakeState.uiData) {}
}

val fakeCharacter = Pokemon(
  name = "Charmander",
  url = "Benson"
)

val fakeState = PokemonListContract.UiState(
  isLoading = false,
  isError = false,
  uiData = listOf(
    fakeCharacter,
    fakeCharacter,
    fakeCharacter,
    fakeCharacter,
    fakeCharacter,
    fakeCharacter,
    fakeCharacter
  )
)




