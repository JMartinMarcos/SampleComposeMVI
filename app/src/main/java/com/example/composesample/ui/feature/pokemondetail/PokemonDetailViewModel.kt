package com.example.composesample.ui.feature.pokemondetail

import com.example.composesample.common.base.BaseViewModel
import com.example.composesample.common.base.ViewEvent
import com.example.composesample.common.base.ViewSideEffect
import com.example.composesample.common.base.ViewState
import com.example.composesample.data.PokemonRepository
import com.example.composesample.model.PokemonDetail
import kotlin.properties.Delegates

class PokemonDetailViewModel(
  private val pokemonRepository: PokemonRepository
) : BaseViewModel<
    PokemonDetailContract.UiState,
    PokemonDetailContract.Event,
    PokemonDetailContract.Effect>(PokemonDetailContract.UiState.initialState) {

  private var pokemonId by Delegates.notNull<Int>()

  override fun handleEvents(event: PokemonDetailContract.Event) {
    when (event) {
      PokemonDetailContract.Event.BackButtonClicked ->
        setEffect { PokemonDetailContract.Effect.Navigation.Back }

      PokemonDetailContract.Event.Retry -> {
        setState { copy(isLoading = true, isError = false) }
        getPokemonDetail(pokemonId)
      }
    }
  }

  fun fetchPokemon(id: Int) {
    pokemonId = id
    getPokemonDetail(id)
  }

  private fun getPokemonDetail(id: Int) {
    execute {
      pokemonRepository.getPokemon(id)
        .onSuccess {
          setState { copy(isLoading = false, uiData = it) }
        }.onFailure {
          setState { copy(isLoading = false, isError = true) }
        }
    }
  }
}

class PokemonDetailContract {

  data class UiState(
    override val isLoading: Boolean = true,
    override val isError: Boolean = false,
    val uiData: PokemonDetail? = null,
  ) : ViewState {
    companion object {
      val initialState = UiState()
    }
  }

  sealed class Event : ViewEvent {
    data object Retry : Event()
    data object BackButtonClicked : Event()
  }

  sealed class Effect : ViewSideEffect {
    sealed class Navigation : Effect() {
      data object Back : Navigation()
    }
  }
}