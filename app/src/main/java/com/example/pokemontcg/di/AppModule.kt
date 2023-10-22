package com.example.pokemontcg.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.example.pokemontcg.data.local.PokemonTcgDatabase
import com.example.pokemontcg.data.preferences.DefaultPreferences
import com.example.pokemontcg.data.remote.api.PokemonTcgApi
import com.example.pokemontcg.data.repository.PokemonCardsRepositoryImpl
import com.example.pokemontcg.domain.preferences.Preferences
import com.example.pokemontcg.domain.repository.PokemonCardsRepository
import com.example.pokemontcg.domain.use_cases.AllGymOpponentsUseCases
import com.example.pokemontcg.domain.use_cases.FilterOutDeckUseCase
import com.example.pokemontcg.domain.use_cases.AllMyDeckUseCases
import com.example.pokemontcg.domain.use_cases.DeleteAllPokemonCardsUseCase
import com.example.pokemontcg.domain.use_cases.DeleteOpponentFromDbUseCase
import com.example.pokemontcg.domain.use_cases.GetAllGymOpponentsUseCase
import com.example.pokemontcg.domain.use_cases.GetAllPokemonInfoFromAPIUseCase
import com.example.pokemontcg.domain.use_cases.InsertGymOpponentUseCase
import com.example.pokemontcg.presentation.features.createdecks.use_cases.DeletePokemonFromDeckUseCase
import com.example.pokemontcg.presentation.features.createdecks.use_cases.GetPokemonFromDeckUseCase
import com.example.pokemontcg.presentation.features.createdecks.use_cases.GetPokemonInfoFromAPIUseCase
import com.example.pokemontcg.presentation.features.createdecks.use_cases.InsertPokemonToDeckUseCase
import com.example.pokemontcg.presentation.features.game.domain.use_cases.DrawCardsUseCase
import com.example.pokemontcg.presentation.features.game.domain.use_cases.GameUseCases
import com.example.pokemontcg.presentation.features.game.domain.use_cases.ShuffleUseCase
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.playerturn.use_cases.ActionInHandUseCase
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.playerturn.use_cases.CardTextInHandUseCase
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.playerturn.use_cases.PlayerTurnUseCases
import com.example.pokemontcg.util.DangerousConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideSharedPreferences(
        app: Application
    ): SharedPreferences {

        return app.getSharedPreferences("shared_prefs", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideDefaultPreferences(
        sharedPreferences: SharedPreferences
    ): Preferences {

        return DefaultPreferences(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun providePokemonTcgApi(
        client: OkHttpClient
    ): PokemonTcgApi {
        return Retrofit.Builder()
            .baseUrl(DangerousConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(PokemonTcgApi::class.java)
    }

    @Provides
    @Singleton
    fun providePokemonCardsRepository(
        api : PokemonTcgApi,
        db : PokemonTcgDatabase
    ): PokemonCardsRepository {
        return PokemonCardsRepositoryImpl(
            api = api,
            dao = db.dao
        )

    }

    @Provides
    @Singleton
    fun providesPokemonTcgDatabase(app:Application) : PokemonTcgDatabase{
        return Room.databaseBuilder(
            app,
            PokemonTcgDatabase::class.java,
            "pokemon_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideTrackerUseCases(
        repository: PokemonCardsRepository,
    ): AllMyDeckUseCases {
        return AllMyDeckUseCases(
            getPokemonFromDeckUseCase = GetPokemonFromDeckUseCase(repository),
            insertPokemonToDeckUseCase = InsertPokemonToDeckUseCase(repository),
            deletePokemonFromDeckUseCase = DeletePokemonFromDeckUseCase(repository),
            deleteAllPokemonCardsUseCase = DeleteAllPokemonCardsUseCase(repository),
            getPokemonInfoFromAPIUseCase = GetPokemonInfoFromAPIUseCase(repository),
            getAllPokemonInfoFromAPIUseCase = GetAllPokemonInfoFromAPIUseCase(repository)
        )
    }

    @Singleton
    @Provides
    fun provideAllGymOpponentsUseCases(
        repository: PokemonCardsRepository,
    ): AllGymOpponentsUseCases {
        return AllGymOpponentsUseCases(

            deleteOpponentFromDbUseCase = DeleteOpponentFromDbUseCase(repository),
            getAllGymOpponentsUseCase = GetAllGymOpponentsUseCase(repository),
            insertGymOpponentUseCase = InsertGymOpponentUseCase(repository)
        )
    }
    @Singleton
    @Provides
    fun provideGameUseCases(
    ): GameUseCases {
        return GameUseCases(
            shuffle = ShuffleUseCase(),
            drawCards = DrawCardsUseCase()
        )
    }

    @Singleton
    @Provides
    fun providePlayerTurnUseCases(
    ): PlayerTurnUseCases {
        return PlayerTurnUseCases(
            cardTextInHand = CardTextInHandUseCase(),
            actionInHand = ActionInHandUseCase()
        )
    }
    @Singleton
    @Provides
    fun provideFilterOutDeck() : FilterOutDeckUseCase{
        return FilterOutDeckUseCase()
    }
}