package com.mobile.ewallet.network

import com.mobile.ewallet.model.api.BaseAPIResponse
import com.mobile.ewallet.model.api.badge.Badge
import com.mobile.ewallet.model.api.badge.BadgeStatus
import com.mobile.ewallet.model.api.dashboard.DashboardBalance
import com.mobile.ewallet.model.api.dashboard.TransactionItem
import com.mobile.ewallet.model.api.detailpokemon.DetailPokemonResponse
import com.mobile.ewallet.model.api.moneyrequest.MoneyRequestData
import com.mobile.ewallet.model.api.pokemon.PokemonResponse
import com.mobile.ewallet.model.api.profile.ProfileAPIResponse
import com.mobile.ewallet.model.api.register.ConfirmOTPAPIResponse
import com.mobile.ewallet.model.api.sendmoney.HistoryTransferTransaction
import com.mobile.ewallet.model.api.sendmoney.banktransfer.*
import com.mobile.ewallet.model.api.sendmoney.bycontact.ContactUser
import com.mobile.ewallet.model.api.sendmoney.bycontact.MinimumNominalContactResponse
import com.mobile.ewallet.model.api.sendmoney.byscan.AdminFeeResponse
import com.mobile.ewallet.model.api.sendmoney.byscan.MinimumNominalResponse
import com.mobile.ewallet.model.api.sendmoney.byscan.SendMoneyResult
import com.mobile.ewallet.model.api.sendmoney.byscan.TransactionDetail
import com.mobile.ewallet.model.api.splashscreen.SplashscreenAPIResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface APIService {
    @FormUrlEncoded
    @POST("KIRIMUANG_Contact_Step1.aspx")
    fun contactLoadNominal(
        @Field("IDMember") idMember: String,
        @Field("IDMember_Beneficiary") idMemberDestination: String
    ): Single<Response<MutableList<MinimumNominalContactResponse>>>

    @FormUrlEncoded
    @POST("KIRIMUANG_Contact_ListContact.aspx")
    fun contacts(
        @Field("IDMember") idMember: String
    ): Single<Response<MutableList<ContactUser>>>

    @FormUrlEncoded
    @POST("KIRIMUANG_BANK_Detail.aspx")
    fun transferTransactionDetail(
        @Field("IDMember") idMember: String,
        @Field("IDTransaksi") idTransaction: String
    ): Single<Response<MutableList<TransactionDetailTransfer>>>

    @FormUrlEncoded
    @POST("KIRIMUANG_BANK_Step4.aspx")
    fun trfSendMoney(
        @Field("IDMember") idMember: String,
        @Field("IDBank") idBank: String,
        @Field("NomorRekening") accountNumber: String,
        @Field("NamaRekening") name: String,
        @Field("Jumlah") amount: String,
        @Field("BiayaAdmin") adminFee: String,
        @Field("TotalPembayaran") total: String
    ): Single<Response<MutableList<SendMoneyResultTrfResponse>>>

    @FormUrlEncoded
    @POST("KIRIMUANG_BANK_Step2.aspx")
    fun trfLoadAdminFee(
        @Field("IDMember") idMember: String,
        @Field("IDBank") idBank: String,
        @Field("NomorRekening") accountNumber: String,
        @Field("Jumlah") amount: String
    ): Single<Response<MutableList<AdminFeeTrfResponse>>>

    @FormUrlEncoded
    @POST("KIRIMUANG_BANK_Step1.aspx")
    fun trfLoadNominal(
        @Field("IDMember") idMember: String,
        @Field("IDBank") idBank: String,
        @Field("NomorRekening") accountNumber: String
    ): Single<Response<MutableList<MinimumNominalTrfResponse>>>

    @POST("DD_BANK.aspx")
    fun listBank(): Single<Response<MutableList<Bank>>>

    @FormUrlEncoded
    @POST("HISTORY_Transaksi_KirimUang.aspx")
    fun transferTransactionHistory(
        @Field("IDMember") idMember: String
    ): Single<Response<MutableList<HistoryTransferTransaction>>>

    @FormUrlEncoded
    @POST("SCAN_Detail.aspx")
    fun transactionDetail(
        @Field("IDMember") idMember: String,
        @Field("IDTransaksi") idTransaction: String
    ): Single<Response<MutableList<TransactionDetail>>>

    @FormUrlEncoded
    @POST("SCAN_Step3.aspx")
    fun scanSendMoney(
        @Field("IDMember") idMember: String,
        @Field("QRCode") qr: String,
        @Field("Jumlah") amount: String,
        @Field("BiayaAdmin") adminFee: String,
        @Field("TotalPembayaran") total: String
    ): Single<Response<MutableList<SendMoneyResult>>>

    @FormUrlEncoded
    @POST("SCAN_Step2.aspx")
    fun scanAdminFee(
        @Field("IDMember") idMember: String,
        @Field("QRCode") qr: String,
        @Field("Jumlah") amount: String
    ): Single<Response<MutableList<AdminFeeResponse>>>

    @FormUrlEncoded
    @POST("SCAN_Step1.aspx")
    fun scanLoadNominal(
        @Field("IDMember") idMember: String,
        @Field("QRCode") qr: String
    ): Single<Response<MutableList<MinimumNominalResponse>>>

    @FormUrlEncoded
    @POST("MINTA_UANG.aspx")
    fun moneyRequest(
        @Field("IDMember") idMember: String
    ): Single<Response<MutableList<MoneyRequestData>>>

    @FormUrlEncoded
    @POST("HISTORY_Transaksi_Kredit.aspx")
    fun creditTransactionHistory(
        @Field("IDMember") idMember: String
    ): Single<Response<MutableList<TransactionItem>>>

    @FormUrlEncoded
    @POST("Badge_Box.aspx")
    fun statusBadge(
        @Field("IDMember") idMember: String
    ): Single<Response<MutableList<BadgeStatus>>>

    @FormUrlEncoded
    @POST("Badge_List.aspx")
    fun listBadge(
        @Field("IDMember") idMember: String
    ): Single<Response<MutableList<Badge>>>

    @FormUrlEncoded
    @POST("HISTORY_Transaksi.aspx")
    fun transactionHistory(
        @Field("IDMember") idMember: String
    ): Single<Response<MutableList<TransactionItem>>>

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
