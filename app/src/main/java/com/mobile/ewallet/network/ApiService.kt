package com.mobile.ewallet.network

import com.mobile.ewallet.model.api.BaseAPIResponse
import com.mobile.ewallet.model.api.badge.Badge
import com.mobile.ewallet.model.api.badge.BadgeStatus
import com.mobile.ewallet.model.api.credit.*
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
import com.mobile.ewallet.model.api.sendmoney.bycontact.SendMoneyContactResult
import com.mobile.ewallet.model.api.sendmoney.bycontact.TransactionDetailContact
import com.mobile.ewallet.model.api.sendmoney.byscan.AdminFeeResponse
import com.mobile.ewallet.model.api.sendmoney.byscan.MinimumNominalResponse
import com.mobile.ewallet.model.api.sendmoney.byscan.SendMoneyResult
import com.mobile.ewallet.model.api.sendmoney.byscan.TransactionDetail
import com.mobile.ewallet.model.api.splashscreen.SplashscreenAPIResponse
import com.mobile.ewallet.model.api.topup.TopupVA
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface APIService {
    @POST("DDM_SumberDana.aspx")
    fun formSumberDana(): Single<Response<MutableList<SumberDana>>>

    @POST("DDM_TempatBekerja.aspx")
    fun formTempatBekerja(): Single<Response<MutableList<TempatBekerja>>>

    @POST("DDM_BidangUsaha.aspx")
    fun formBidangUsaha(): Single<Response<MutableList<BidangUsaha>>>

    @POST("DDM_Jabatan.aspx")
    fun formJabatan(): Single<Response<MutableList<Jabatan>>>

    @POST("DDM_Profesi.aspx")
    fun formProfesi(): Single<Response<MutableList<Profesi>>>

    @POST("DDM_Kewarganegaraan.aspx")
    fun formKewarganegaraan(): Single<Response<MutableList<Kewarganegaraan>>>

    @FormUrlEncoded
    @POST("Pendanaan_KUM_PreScreening.aspx")
    fun preScreeningKUM(
        @Field("IDMember") idmember: String,
        @Field("IDRequest") idRequest: String,
        @Field("NamaPelaporan") namaPelapor: String,
        @Field("NomorKartuKeluarga") nomorKK: String,
        @Field("TempatLahir") tempatLahir: String,
        @Field("JenisKelamin") jenisKelamin: String,
        @Field("TanggalLahir") tanggalLahir: String,
        @Field("PendidikanTerakhir") pendidikanTerakhir: String,
        @Field("NamaGadisIbuKandung") namaIbu: String,
        @Field("telpygdptdihubungiArea") telpArea: String,
        @Field("telpygdptdihubungi") telp: String,
        @Field("NomorKTP") nomorKTP: String,
        @Field("NamaSesuaiKTP") namaKTP: String,
        @Field("AlamatSesuaiKTP") alamatKTP: String,
        @Field("KotaSesuaiKTP") kotaKTP: String,
        @Field("KecamatanSesuaiKTP") kecamatanKTP: String,
        @Field("KelurahanSesuaiKTP") kelurahanKTP: String,
        @Field("KodePosKTP") kodePosKTP: String,
        @Field("AlamatRumah") alamatRumah: String,
        @Field("KotaAlamatRumah") kotaRumah: String,
        @Field("KecamatanRumah") kecamatanRumah: String,
        @Field("KelurahanRumah") kelurahanRumah: String,
        @Field("KodePosRumah") kodePosRumah: String,
        @Field("LokasiDatiIIRumah") datillRumah: String,
        @Field("StatusRumah") statusRumah: String,
        @Field("MulaiMenempati") tanggalMenempatiRumah: String,
        @Field("StatusPernikahan") statusPernikahan: String,
        @Field("NamaSuamiIstri") namaPasangan: String = "",
        @Field("TanggalLahirPasangan") tanggalLahirPasangan: String = "",
        @Field("NomorKTPPasangan") nomorKTPPasangan: String = "",
        @Field("JenisKredit") jenisKredit: String,
        @Field("LimitAwalyangdiminta") limitAwal: String,
        @Field("JangkaWaktu") jangkaWaktu: String,
        @Field("NPWP") npwp: String
    ): Single<Response<MutableList<KUMPrescreeningResponse>>>

    @FormUrlEncoded
    @POST("Pendanaan_IDRequest.aspx")
    fun generateCreditRequest(
        @Field("IDMember") idmember: String,
    ): Single<Response<MutableList<GeneratedCreditRequestId>>>

    @FormUrlEncoded
    @POST("DDM_JangkaWaktu.aspx")
    fun formJangkaWaktu(
        @Field("JenisKredit") jenisKredit: String,
    ): Single<Response<MutableList<JangkaWaktu>>>

    @FormUrlEncoded
    @POST("DDM_JenisKredit_Parameter.aspx")
    fun jenisKreditParameter(
        @Field("JenisKredit") jenisKredit: String,
    ): Single<Response<MutableList<JenisKreditParameter>>>

    @POST("DDM_JenisKredit_KUM.aspx")
    fun formJenisKreditKUM(): Single<Response<MutableList<JenisKredit>>>

    @POST("DDM_StatusPernikahan.aspx")
    fun formStatusPernikahan(): Single<Response<MutableList<StatusPernikahan>>>

    @POST("DDM_StatusRumah.aspx")
    fun formStatusRumah(): Single<Response<MutableList<StatusRumah>>>

    @POST("DDM_LokasiDatiII.aspx")
    fun formlokasiDatill(): Single<Response<MutableList<LokasiDatill>>>

    @FormUrlEncoded
    @POST("DDM_KodePos.aspx")
    fun formKodePos(
        @Field("Keyword") keyword: String,
    ): Single<Response<MutableList<KodePos>>>

    @POST("DDM_PendidikanTerakhir.aspx")
    fun formPendidikanTerakhir(): Single<Response<MutableList<Pendidikan>>>

    @POST("DDM_JenisKelamin.aspx")
    fun formJenisKelamin(): Single<Response<MutableList<JenisKelamin>>>

    @FormUrlEncoded
    @POST("TOPUP_List_VA.aspx")
    fun listVirtualAccount(
        @Field("IDMember") idMember: String,
    ): Single<Response<MutableList<TopupVA>>>

    @FormUrlEncoded
    @POST("KIRIMUANG_Contact_Detail.aspx")
    fun contactTransactionDetail(
        @Field("IDMember") idMember: String,
        @Field("IDTransaksi") idTransaction: String
    ): Single<Response<MutableList<TransactionDetailContact>>>

    @FormUrlEncoded
    @POST("KIRIMUANG_Contact_Step3.aspx")
    fun contactSendMoney(
        @Field("IDMember") idMember: String,
        @Field("IDMember_Beneficiary") idMemberDestination: String,
        @Field("Jumlah") amount: String,
        @Field("BiayaAdmin") adminFee: String,
        @Field("TotalPembayaran") total: String
    ): Single<Response<MutableList<SendMoneyContactResult>>>

    @FormUrlEncoded
    @POST("KIRIMUANG_Contact_Step2.aspx")
    fun contactAdminFee(
        @Field("IDMember") idMember: String,
        @Field("IDMember_Beneficiary") idMemberDestination: String,
        @Field("Jumlah") amount: String
    ): Single<Response<MutableList<AdminFeeResponse>>>

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
