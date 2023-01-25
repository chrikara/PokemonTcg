package com.example.pokemontcg.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokemontcg.data.local.entity.PokemonEntity
import com.example.pokemontcg.data.local.entity.TrackerDao

@Database(
    entities = [PokemonEntity::class],
    version = 1
)
abstract class PokemonTcgDatabase : RoomDatabase() {

    abstract val dao: TrackerDao
}