package com.mobile.ewallet.network

import com.mobile.ewallet.model.api.BaseAPIResponse
import com.mobile.ewallet.model.api.dashboard.DashboardBalance
import com.mobile.ewallet.model.api.detailpokemon.DetailPokemonResponse
import com.mobile.ewallet.model.api.pokemon.PokemonResponse
import com.mobile.ewallet.model.api.profile.ProfileAPIResponse
import com.mobile.ewallet.model.api.register.ConfirmOTPAPIResponse
import com.mobile.ewallet.model.api.splashscreen.SplashscreenAPIResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface APIService {
    @FormUrlEncoded
    @POST("DASHBOARD.aspx")
    fun dashboardBalance(
        @Field("IDMember") idMember: String
    ): Single<Response<MutableList<DashboardBalance>>>

    @FormUrlEncoded
    @POST("Profile_SimpanData.aspx")
    fun saveProfile(
        @Field("IDMember") idMember: String,
        @Field("NOWA") phone: String,
        @Field("NamaLengkap") name: String,
        @Field("TanggalLahir") birthDate: String,
        @Field("NIK") nik: String
    ): Single<Response<MutableList<BaseAPIResponse>>>

    @Multipart
    @POST("Profile_Photo_Update.aspx")
    fun uploadPhotoProfile(
        @Part("IDMember") idMember: RequestBody,
        @Part file: MultipartBody.Part
    ): Single<Response<MutableList<BaseAPIResponse>>>

    @FormUrlEncoded
    @POST("Profile_DATA.aspx")
    fun loadUserProfile(
        @Field("IDMember") idMember: String
    ): Single<Response<MutableList<ProfileAPIResponse>>>

    @FormUrlEncoded
    @POST("Login_OTP.aspx")
    fun confirmOtpLogin(
        @Field("NOWA") phone: String,
        @Field("RegID") uuid: String,
        @Field("OTP") otp: String
    ): Single<Response<MutableList<ConfirmOTPAPIResponse>>>

    @FormUrlEncoded
    @POST("Login_OTP_Resend.aspx")
    fun resendOTPLogin(
        @Field("NOWA") phone: String,
        @Field("RegID") uuid: String
    ): Single<Response<MutableList<BaseAPIResponse>>>

    @FormUrlEncoded
    @POST("Login.aspx")
    fun reqOTPLogin(
        @Field("NOWA") phone: String,
        @Field("RegID") uuid: String
    ): Single<Response<MutableList<BaseAPIResponse>>>

    @FormUrlEncoded
    @POST("Login_First_SimpanData.aspx")
    fun finishRegister(
        @Field("NOWA") phone: String,
        @Field("IDMember") idmember: String,
        @Field("NamaLengkap") fullName: String
    ): Single<Response<MutableList<BaseAPIResponse>>>

    @FormUrlEncoded
    @POST("Login_First_OTP.aspx")
    fun confirmOtpRegister(
        @Field("NOWA") phone: String,
        @Field("RegID") uuid: String,
        @Field("OTP") otp: String
    ): Single<Response<MutableList<ConfirmOTPAPIResponse>>>

    @FormUrlEncoded
    @POST("Login_First_Resend.aspx")
    fun reqResendOTPRegister(
        @Field("NOWA") phone: String,
        @Field("RegID") uuid: String
    ): Single<Response<MutableList<BaseAPIResponse>>>

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
