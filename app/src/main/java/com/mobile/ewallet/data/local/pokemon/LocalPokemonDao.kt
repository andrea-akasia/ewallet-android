package com.mobile.ewallet.data.local.pokemon

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface LocalPokemonDao {
    @Query("SELECT * FROM pokemon")
    fun loadAllPokemonPaged(): DataSource.Factory<Int, LocalPokemon>

    @Query("SELECT * FROM pokemon")
    fun loadAllPokemon(): List<LocalPokemon>

    @Query("SELECT * FROM pokemon WHERE pokemon_name=:name")
    fun loadPokemonById(name: String): LocalPokemon

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllPokemon(pokemonList: List<LocalPokemon>)

    @Query("DELETE FROM pokemon")
    fun deleteAllPokemon()

    @Transaction
    fun renewAllData(pokemonList: List<LocalPokemon>) {
        deleteAllPokemon()
        saveAllPokemon(pokemonList)
    }
}