package com.example.composesample.di

import com.example.composesample.ui.feature.pokemondetail.PokemonDetailViewModel
import com.example.composesample.ui.feature.pokemonlist.PokemonListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
  viewModel {
    PokemonListViewModel(pokemonRepository = get())
  }

  viewModel {
    PokemonDetailViewModel(pokemonRepository = get())
  }
}
