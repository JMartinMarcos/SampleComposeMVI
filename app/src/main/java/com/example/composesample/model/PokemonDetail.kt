package com.example.composesample.model

data class PokemonDetail(
  val id: Int,
  val name: String,
  val height: Int,
  val weight: Int,
  val sprites: Sprites,
  val types: List<PokemonTypeSlot>
)

data class Sprites(
  val front_default: String
)

data class PokemonTypeSlot(
  val slot: Int,
  val type: PokemonType
)

data class PokemonType(
  val name: String
)