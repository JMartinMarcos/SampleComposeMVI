package com.example.composesample.data

import com.example.composesample.model.Pokemon
import com.example.composesample.model.PokemonDetail

interface PokemonRepository {
  suspend fun loadPokemon(): Result<List<Pokemon>>
  suspend fun getPokemon(id:Int): Result<PokemonDetail>
}
