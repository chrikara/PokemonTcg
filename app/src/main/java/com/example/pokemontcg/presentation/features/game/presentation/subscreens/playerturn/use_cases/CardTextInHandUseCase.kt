package com.example.pokemontcg.presentation.features.game.presentation.subscreens.playerturn.use_cases

import com.example.pokemontcg.presentation.features.game.domain.model.EnergyCard
import com.example.pokemontcg.presentation.features.game.domain.model.GameCard
import com.example.pokemontcg.presentation.features.game.domain.model.PokemonCard
import com.example.pokemontcg.presentation.features.game.domain.model.PokemonType

class CardTextInHandUseCase {

    operator fun invoke(
        gameCard : GameCard
    ) : String{

        var text = ""

        when (gameCard) {
            is EnergyCard -> {text = "Attach"}
            is PokemonCard -> {
                text = when(gameCard.pokemonType){
                    PokemonType.Basic -> "Add Bench"
                    PokemonType.Stage1 -> "Evolve"
                    PokemonType.Stage2 -> "Evolve"
                }
            }
        }
        return text
    }
}
