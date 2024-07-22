package com.example.composesample.ui.feature.pokemonlist

import com.example.composesample.common.base.BaseViewModel
import com.example.composesample.common.base.ViewEvent
import com.example.composesample.common.base.ViewSideEffect
import com.example.composesample.common.base.ViewState
import com.example.composesample.data.PokemonRepository
import com.example.composesample.model.Pokemon

class PokemonListViewModel(
  val pokemonRepository: PokemonRepository
) : BaseViewModel<
    PokemonListContract.UiState,
    PokemonListContract.Event,
    PokemonListContract.Effect>(PokemonListContract.UiState.initialState) {

  init {
    getPokemon()
  }

  override fun handleEvents(event: PokemonListContract.Event) {
    when (event) {
      PokemonListContract.Event.Retry -> {
        setState { copy(isLoading = true, isError = false) }
        getPokemon()
      }

      PokemonListContract.Event.BackButtonClicked -> setEffect { PokemonListContract.Effect.Navigation.Back }
      PokemonListContract.Event.OrderButtonClicked -> setState {
        getPokemon(!isOrderDescend)
        copy(isOrderDescend = !isOrderDescend, isLoading = true)
      }

      is PokemonListContract.Event.OnPokemonClicked ->
        setEffect { PokemonListContract.Effect.Navigation.Detail(event.id) }

    }
  }

  private fun getPokemon(isOrderDescend: Boolean = true) {
    execute {
      pokemonRepository.loadPokemon()
        .onSuccess {
          setState {
            copy(isLoading = false, uiData = orderResult(isOrderDescend, it), isError = false)
          }
        }
        .onFailure {
          setState { copy(isLoading = false, isError = true) }
        }
    }
  }

  private fun orderResult(
    isOrderDescend: Boolean,
    it: List<Pokemon>
  ) = if (isOrderDescend) it.sortedByDescending { it.name } else it.sortedBy { it.name }
}

class PokemonListContract {

  data class UiState(
    override val isLoading: Boolean = true,
    override val isError: Boolean = false,
    val uiData: List<Pokemon> = emptyList(),
    val isOrderDescend: Boolean = true
  ) : ViewState {
    companion object {
      val initialState = UiState()
    }
  }

  sealed class Event : ViewEvent {
    data object Retry : Event()
    data object BackButtonClicked : Event()
    data object OrderButtonClicked : Event()
    data class OnPokemonClicked(val id: Int) : Event()
  }

  sealed class Effect : ViewSideEffect {
    sealed class Navigation : Effect() {
      data object Back : Navigation()
      data class Detail(val id: Int) : Navigation()
    }
  }
}