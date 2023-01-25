package com.example.pokemontcg.presentation.features.createdecks.use_cases

import com.example.pokemontcg.data.remote.api.dto.cardoverviewdto.Data
import com.example.pokemontcg.data.remote.api.dto.cardoverviewdto.toCardOverViewList
import com.example.pokemontcg.domain.model.CardOverview
import com.example.pokemontcg.domain.repository.PokemonCardsRepository
import com.example.pokemontcg.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPokemonInfoFromAPIUseCase @Inject constructor(
    private val repository: PokemonCardsRepository
) {

    operator fun invoke(pokemonId : String) : Flow<Resource<CardOverview>> = flow {
        try {
            emit(Resource.Loading<CardOverview>())
            val cardInfo = repository.getCardById(pokemonId).data
            val cardOverview = CardOverview(

                    id = cardInfo.id,
                    name = cardInfo.name,
                    imgString = cardInfo.images.large,
                    type = cardInfo.types?.get(0),
                    nationalDex = cardInfo.nationalPokedexNumbers?.get(0)
            )

            emit(Resource.Success<CardOverview>(data = cardOverview))
        } catch(e: HttpException) {
            emit(Resource.Error<CardOverview>(message = e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<CardOverview>(message = "Couldn't reach server. Check your internet connection."))
        }
    }
}