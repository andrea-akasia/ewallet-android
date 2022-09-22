package com.mobile.ewallet.network

import com.mobile.ewallet.model.api.pokemon.PokemonResponse
import com.mobile.ewallet.model.api.detailpokemon.DetailPokemonResponse
import com.mobile.ewallet.model.api.splashscreen.SplashscreenAPIResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface APIService {
    @POST("Splash_Screen.aspx")
    fun splashscreen(): Single<Response<MutableList<SplashscreenAPIResponse>>>

    @GET("pokemon/{name}")
    fun requestDetailPokemon(
        @Path("name") name: String
    ): Single<Response<DetailPokemonResponse>>

    @GET("pokemon")
    suspend fun requestListPokemon(
        @Query("limit") limit: Int,
        @Query("offset") page: Int
    ): Response<PokemonResponse>
}
