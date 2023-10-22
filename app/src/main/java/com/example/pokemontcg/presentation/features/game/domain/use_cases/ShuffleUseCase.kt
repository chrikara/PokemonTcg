package com.example.pokemontcg.presentation.features.game.domain.use_cases

import com.example.pokemontcg.presentation.features.game.domain.model.GameCard

class ShuffleUseCase {

    operator fun invoke(list : MutableList<GameCard>) : MutableList<GameCard>{
        return list.shuffled().toMutableList()
    }
}
