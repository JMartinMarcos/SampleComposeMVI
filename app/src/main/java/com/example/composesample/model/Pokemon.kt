package com.example.composesample.model

import com.google.gson.annotations.SerializedName

data class PokemonList(
    @SerializedName("results") val results: List<Pokemon>
)

data class Pokemon(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
) {

    val id: Int
        get() {
            val components = url.split("/")
            return components[components.size - 2].toIntOrNull() ?: 0
        }

    val imageUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}