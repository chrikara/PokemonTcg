package com.example.pokemontcg.presentation.features.game.domain.use_cases

import com.example.pokemontcg.presentation.features.game.domain.model.GameCard

class DrawCardsUseCase {

    operator fun invoke(numberToDraw : Int, deck : MutableList<GameCard>) : MutableList<GameCard> {
        val currentHand = mutableListOf<GameCard>()

        for (i in 0 until numberToDraw){
            currentHand.add(deck[i])
        }
        deck.removeAll { deck.indexOf(it) < 7 }

        return currentHand
    }
}
