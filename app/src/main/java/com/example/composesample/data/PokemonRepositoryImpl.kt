package com.example.composesample.data

import com.example.composesample.model.Pokemon
import com.example.composesample.model.PokemonDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class PokemonRepositoryImpl(
  private val pokemonAPI: PokemonAPI,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : PokemonRepository {

  override suspend fun loadPokemon(): Result<List<Pokemon>> = makeApiCall(dispatcher) {
    delay(1000)
    pokemonAPI.loadPokemon().results
  }

  override suspend fun getPokemon(id: Int): Result<PokemonDetail> = makeApiCall(dispatcher) {
    delay(1000)
    pokemonAPI.getPokemon(id)
  }
}

suspend fun <T> makeApiCall(
  dispatcher: CoroutineDispatcher,
  call: suspend () -> T
): Result<T> = runCatching {
  withContext(dispatcher) {
    call.invoke()
  }
}
