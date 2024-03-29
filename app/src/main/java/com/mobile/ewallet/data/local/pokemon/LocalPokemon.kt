package com.mobile.ewallet.data.local.pokemon

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "pokemon", primaryKeys = ["pokemon_id", "pokemon_name"])
data class LocalPokemon(
    @ColumnInfo(name = "pokemon_id") val id: Int,
    @ColumnInfo(name = "pokemon_name") val pokemonName: String = "",
    @ColumnInfo(name = "pokemon_image_url") val pokemonImageUrl: String = ""

    //TODO enable this to test migration from v1 to v2
    //,@ColumnInfo(name = "pokemon_type") val pokemonType: String? = null
)