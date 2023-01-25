package com.example.pokemontcg.di

import android.app.Application
import androidx.room.Room
import com.example.pokemontcg.data.local.PokemonTcgDatabase
import com.example.pokemontcg.data.remote.api.PokemonTcgApi
import com.example.pokemontcg.data.repository.PokemonCardsRepositoryImpl
import com.example.pokemontcg.domain.repository.PokemonCardsRepository
import com.example.pokemontcg.presentation.features.createdecks.use_cases.AllMyDeckUseCases
import com.example.pokemontcg.presentation.features.createdecks.use_cases.DeletePokemonFromDeckUseCase
import com.example.pokemontcg.presentation.features.createdecks.use_cases.GetPokemonFromDeckUseCase
import com.example.pokemontcg.presentation.features.createdecks.use_cases.InsertPokemonToDeckUseCase
import com.example.pokemontcg.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.internal.addHeaderLenient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
    fun providePokemonTcgApi(client: OkHttpClient): PokemonTcgApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
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
            deletePokemonFromDeckUseCase = DeletePokemonFromDeckUseCase(repository)
        )

    }
}