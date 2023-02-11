package com.example.pokemontcg.presentation.features.game

import com.example.pokemontcg.domain.model.Symbol
import com.example.pokemontcg.presentation.features.game.domain.model.EnergyCard
import com.example.pokemontcg.presentation.features.game.domain.model.Player
import com.example.pokemontcg.presentation.features.game.domain.model.PokemonCard
import com.example.pokemontcg.presentation.features.game.domain.model.PokemonType

data class GameState(
    val player: Player = Player("Player"
//        , currentPokemon = fakePlayerCurrentPokemon, currentHand = mutableListOf(
//        fakeHandEnergyCard, fakePlayerBasicPokemon, fakePlayerStage1Pokemon, fakePlayerStage2Pokemon)
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
            object HAND : GameSealedClass()
            object CARD_INFO : GameSealedClass()
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
    GameState.GameSealedClass.PLAYER_TURN.HAND,
    GameState.GameSealedClass.PLAYER_TURN.CARD_INFO,
)

val fakeHandEnergyCard = EnergyCard(
    name = "Psychic",
    symbol = Symbol.Psychic

)
val fakePlayerBasicPokemon = PokemonCard(
    baseId = "base1-3",
    name = "Chansey",
    image = "",
    nationalDex = 113,
    pokemonType = PokemonType.Basic
)

val fakePlayerStage2Pokemon = PokemonCard(
    baseId = "base1-1",
    name = "Alakazam",
    image = "",
    nationalDex = 65,
    pokemonType = PokemonType.Stage2
)
val fakePlayerStage1Pokemon = PokemonCard(
    baseId = "base1-6",
    name = "Gyarados",
    image = "",
    nationalDex = 130,
    pokemonType = PokemonType.Stage1
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


