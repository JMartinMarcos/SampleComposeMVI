package com.example.composesample.di

import com.example.composesample.data.PokemonRepository
import com.example.composesample.data.PokemonRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<PokemonRepository> {
        PokemonRepositoryImpl(
            pokemonAPI = get()
        )
    }
}