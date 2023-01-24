package com.example.pokemontcg.domain.use_cases

import com.example.pokemontcg.data.remote.api.dto.toCardOverViewList
import com.example.pokemontcg.domain.model.CardOverview
import com.example.pokemontcg.domain.repository.PokemonCardsRepository
import com.example.pokemontcg.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(
    private val repository: PokemonCardsRepository
) {

    operator fun invoke() : Flow<Resource<List<CardOverview>>> = flow {
        try {
            emit(Resource.Loading<List<CardOverview>>())
            val coins = repository.getCards().data.map { it.toCardOverViewList() }
            emit(Resource.Success<List<CardOverview>>(data = coins))
        } catch(e: HttpException) {
            emit(Resource.Error<List<CardOverview>>(message = e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<CardOverview>>(message = "Couldn't reach server. Check your internet connection."))
        }
    }
}