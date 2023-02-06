package com.example.pokemontcg.data.local.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokemontcg.domain.model.GymOpponent
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonToDeck(entity: PokemonEntity)

    @Delete
    suspend fun deletePokemonFromDeck(entity: PokemonEntity)

    @Query(
        """
            SELECT *
            FROM pokemonentity
         """
    )
    fun getPokemonFromDeck() : Flow<List<PokemonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGymOpponentToDb(entity: GymOpponentEntity)

    @Query("Delete from gymopponententity")
    suspend fun deleteAllGymOpponents()

    @Query("Delete from pokemonentity")
    suspend fun deleteAllPokemonSavedCards()

    @Query(
        """
            SELECT *
            FROM gymopponententity
         """
    )
    fun getAllGymOpponents() : Flow<List<GymOpponentEntity>>

}