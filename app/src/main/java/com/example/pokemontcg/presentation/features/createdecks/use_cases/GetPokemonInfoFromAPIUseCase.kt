package com.example.pokemontcg.presentation.features.createdecks.use_cases


import com.example.pokemontcg.data.remote.api.dto.cardinfodto.Data
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

    operator fun invoke(pokemonId : String) : Flow<Resource<Data>> = flow {
        try {
            emit(Resource.Loading<Data>())
            val cardInfo = repository.getCardById(pokemonId).data


            emit(Resource.Success<Data>(data = cardInfo))
        } catch(e: HttpException) {
            emit(Resource.Error<Data>(message = e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<Data>(message = "Couldn't reach server. Check your internet connection."))
        }
    }
}