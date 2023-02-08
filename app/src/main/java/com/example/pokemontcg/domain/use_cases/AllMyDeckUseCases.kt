package com.example.pokemontcg.domain.use_cases

import com.example.pokemontcg.presentation.features.createdecks.use_cases.DeletePokemonFromDeckUseCase
import com.example.pokemontcg.presentation.features.createdecks.use_cases.GetPokemonFromDeckUseCase
import com.example.pokemontcg.presentation.features.createdecks.use_cases.GetPokemonInfoFromAPIUseCase
import com.example.pokemontcg.presentation.features.createdecks.use_cases.InsertPokemonToDeckUseCase

data class AllMyDeckUseCases(
    val getPokemonInfoFromAPIUseCase: GetPokemonInfoFromAPIUseCase,
    val getAllPokemonInfoFromAPIUseCase : GetAllPokemonInfoFromAPIUseCase,


    val getPokemonFromDeckUseCase: GetPokemonFromDeckUseCase,
    val insertPokemonToDeckUseCase: InsertPokemonToDeckUseCase,
    val deletePokemonFromDeckUseCase: DeletePokemonFromDeckUseCase,
    val deleteAllPokemonCardsUseCase : DeleteAllPokemonCardsUseCase
)