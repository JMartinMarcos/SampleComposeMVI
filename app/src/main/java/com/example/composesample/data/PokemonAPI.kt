package com.example.composesample.data


import com.example.composesample.model.PokemonDetail
import com.example.composesample.model.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonAPI {

  @GET(Endpoints.GET_POKEMON)
  suspend fun loadPokemon(): PokemonList

  @GET(Endpoints.GET_POKEMON_BY_ID)
  suspend fun getPokemon(@Path("id") id: Int): PokemonDetail
}

