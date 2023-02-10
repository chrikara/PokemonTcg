package com.example.pokemontcg.presentation.features.game

import com.example.pokemontcg.presentation.features.game.domain.model.Player
import com.example.pokemontcg.presentation.features.game.domain.model.PokemonCard
import com.example.pokemontcg.presentation.features.game.domain.model.PokemonType

data class GameState(
    val player: Player = Player("Player"//, currentPokemon = fakePlayerCurrentPokemon
    )
    ,
    val opponent: Player = Player("Opponent"//, currentPokemon = fakeOpponentCurrentPokemon
    ),
    val currentState : GameSealedClass = GameSealedClass.LOADING,
    val errorMessageAPI : String = "",
    val isBackIntercepted : Boolean = false,

    ){


    sealed class GameSealedClass {

        object LOADING : GameSealedClass()
        object ERROR : GameSealedClass()
        object START : GameSealedClass()
        sealed class CHOOSE_ACTIVE : GameSealedClass() {
            object EXPLANATION : CHOOSE_ACTIVE()
            object HAND : CHOOSE_ACTIVE()
            object CARD_INFO : CHOOSE_ACTIVE()
        }
        sealed class CHOOSE_BENCH : GameSealedClass(){
            object EXPLANATION : CHOOSE_BENCH()
            object HAND : CHOOSE_BENCH()
            object CARD_INFO : CHOOSE_BENCH()
        }
        object PLAYER_TURN : GameSealedClass(){
            object EXPLANATION : GameSealedClass()
            object MAIN : GameSealedClass()
        }
        object OPPONENT_TURN : GameSealedClass()
        object FLIP_COIN : GameSealedClass()
        object PLAYER_WON : GameSealedClass()
        object OPPONENT_WON : GameSealedClass()

    }


}
val GAMESTATE_CHOOSE_ACTIVE_LIST = listOf(
    GameState.GameSealedClass.CHOOSE_ACTIVE.EXPLANATION,
    GameState.GameSealedClass.CHOOSE_ACTIVE.HAND,
    GameState.GameSealedClass.CHOOSE_ACTIVE.CARD_INFO,
)

val GAMESTATE_CHOOSE_BENCH_LIST = listOf(
    GameState.GameSealedClass.CHOOSE_BENCH.EXPLANATION,
    GameState.GameSealedClass.CHOOSE_BENCH.HAND,
    GameState.GameSealedClass.CHOOSE_BENCH.CARD_INFO,
)

val GAMESTATE_PLAYER_TURN_LIST = listOf(
    GameState.GameSealedClass.PLAYER_TURN.EXPLANATION,
    GameState.GameSealedClass.PLAYER_TURN.MAIN,
)

val fakePlayerCurrentPokemon = PokemonCard(
    baseId = "base1-1",
    name = "Alakazam",
    image = "",
    nationalDex = 65,
    pokemonType = PokemonType.Stage2
)

val fakeOpponentCurrentPokemon = PokemonCard(
    baseId = "base1-1",
    name = "Mewtwo",
    image = "",
    nationalDex = 151,
    pokemonType = PokemonType.Basic
)


