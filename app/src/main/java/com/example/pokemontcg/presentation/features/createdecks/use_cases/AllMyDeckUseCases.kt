package com.example.pokemontcg.presentation.features.createdecks.use_cases

data class AllMyDeckUseCases(
    val getPokemonFromDeckUseCase: GetPokemonFromDeckUseCase,
    val insertPokemonToDeckUseCase: InsertPokemonToDeckUseCase,
    val deletePokemonFromDeckUseCase: DeletePokemonFromDeckUseCase
)