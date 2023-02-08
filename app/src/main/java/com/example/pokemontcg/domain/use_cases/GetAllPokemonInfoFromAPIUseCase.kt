package com.example.pokemontcg.domain.use_cases

import com.example.pokemontcg.data.remote.api.dto.cardoverviewdto.Data
import com.example.pokemontcg.domain.repository.PokemonCardsRepository
import com.example.pokemontcg.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllPokemonInfoFromAPIUseCase @Inject constructor(
    private val repository: PokemonCardsRepository
) {

    operator fun invoke() : Flow<Resource<List<Data>>> = flow {
        try {
            emit(Resource.Loading<List<Data>>())
            val cards = repository.getCards().data
            emit(Resource.Success<List<Data>>(data = cards))
        } catch(e: HttpException) {
            emit(Resource.Error<List<Data>>(message = e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<Data>>(message = "Couldn't reach server. Check your internet connection."))
        }
    }
}

