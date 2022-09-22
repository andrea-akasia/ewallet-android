package com.mobile.ewallet.network

import com.mobile.ewallet.model.api.BaseAPIResponse
import com.mobile.ewallet.model.api.pokemon.PokemonResponse
import com.mobile.ewallet.model.api.detailpokemon.DetailPokemonResponse
import com.mobile.ewallet.model.api.splashscreen.SplashscreenAPIResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*


interface APIService {

    @FormUrlEncoded
    @POST("Login_First.aspx")
    fun reqOTPRegister(
        @Field("NOWA") phone: String,
        @Field("RegID") uuid: String
    ): Single<Response<MutableList<BaseAPIResponse>>>

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
