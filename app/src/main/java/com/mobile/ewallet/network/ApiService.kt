package com.mobile.ewallet.network

import com.mobile.ewallet.model.api.BaseAPIResponse
import com.mobile.ewallet.model.api.VersioningResponse
import com.mobile.ewallet.model.api.badge.Badge
import com.mobile.ewallet.model.api.badge.BadgeStatus
import com.mobile.ewallet.model.api.credit.*
import com.mobile.ewallet.model.api.credit.billing.BillingCredit
import com.mobile.ewallet.model.api.credit.billing.BillingTransaction
import com.mobile.ewallet.model.api.credit.billing.BillingVA
import com.mobile.ewallet.model.api.credit.detailrequest.kum.DetailKUMDocument
import com.mobile.ewallet.model.api.credit.detailrequest.kum.DetailKUMFulfillment
import com.mobile.ewallet.model.api.credit.detailrequest.kum.DetailKUMPrescreening
import com.mobile.ewallet.model.api.credit.detailrequest.kur.DetailKURDocument
import com.mobile.ewallet.model.api.credit.detailrequest.kur.DetailKURFulfillment
import com.mobile.ewallet.model.api.credit.detailrequest.kur.DetailKURPrescreening
import com.mobile.ewallet.model.api.credit.preview.KUMPreviewResponse
import com.mobile.ewallet.model.api.credit.preview.KURPreviewResponse
import com.mobile.ewallet.model.api.dashboard.DashboardBalance
import com.mobile.ewallet.model.api.dashboard.TransactionItem
import com.mobile.ewallet.model.api.detailpokemon.DetailPokemonResponse
import com.mobile.ewallet.model.api.moneyrequest.MoneyRequestData
import com.mobile.ewallet.model.api.pokemon.PokemonResponse
import com.mobile.ewallet.model.api.profile.Faq
import com.mobile.ewallet.model.api.profile.ProfileAPIResponse
import com.mobile.ewallet.model.api.profile.TermsCondition
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
import com.mobile.ewallet.model.api.topup.TopupInstruction
import com.mobile.ewallet.model.api.topup.TopupVA
import com.mobile.ewallet.model.api.topup.TopupViaKreditResultResponse
import com.mobile.ewallet.model.api.topup.TopupViaKreditStatResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface APIService {
    @FormUrlEncoded
    @POST("Pendanaan_KUR_Preview.aspx")
    fun previewKUR(
        @Field("IDMember") idMember: String,
        @Field("IDRequest") idRequest: String
    ): Single<Response<MutableList<KURPreviewResponse>>>

    @POST("Versioning.aspx")
    fun versioning(): Single<Response<MutableList<VersioningResponse>>>

    @FormUrlEncoded
    @POST("Pendanaan_KUM_Preview.aspx")
    fun previewKUM(
        @Field("IDMember") idMember: String,
        @Field("IDRequest") idRequest: String
    ): Single<Response<MutableList<KUMPreviewResponse>>>

    @FormUrlEncoded
    @POST("Pendanaan_DETAIL_KUR3.aspx")
    fun detailKURDocument(
        @Field("IDMember") idMember: String,
        @Field("IDPendanaan") idPendanaan: String
    ): Single<Response<MutableList<DetailKURDocument>>>

    @FormUrlEncoded
    @POST("Pendanaan_DETAIL_KUR2.aspx")
    fun detailKURFulfillment(
        @Field("IDMember") idMember: String,
        @Field("IDPendanaan") idPendanaan: String
    ): Single<Response<MutableList<DetailKURFulfillment>>>

    @FormUrlEncoded
    @POST("Pendanaan_DETAIL_KUR.aspx")
    fun detailKURPrescreening(
        @Field("IDMember") idMember: String,
        @Field("IDPendanaan") idPendanaan: String
    ): Single<Response<MutableList<DetailKURPrescreening>>>

    @FormUrlEncoded
    @POST("Pendanaan_DETAIL_KUM.aspx")
    fun detailKUMPrescreening(
        @Field("IDMember") idMember: String,
        @Field("IDPendanaan") idPendanaan: String
    ): Single<Response<MutableList<DetailKUMPrescreening>>>

    @FormUrlEncoded
    @POST("Pendanaan_DETAIL_KUM2.aspx")
    fun detailKUMFulfillment(
        @Field("IDMember") idMember: String,
        @Field("IDPendanaan") idPendanaan: String
    ): Single<Response<MutableList<DetailKUMFulfillment>>>

    @FormUrlEncoded
    @POST("Pendanaan_DETAIL_KUM3.aspx")
    fun detailKUMDocument(
        @Field("IDMember") idMember: String,
        @Field("IDPendanaan") idPendanaan: String
    ): Single<Response<MutableList<DetailKUMDocument>>>

    @FormUrlEncoded
    @POST("Pendanaan_CANCEL_Pengajuan.aspx")
    fun cancelPendanaan(
        @Field("IDMember") idMember: String,
        @Field("IDPendanaan") idPendanaan: String
    ): Single<Response<MutableList<BaseAPIResponse>>>

    @FormUrlEncoded
    @POST("Box_Pendanaan.aspx")
    fun pendanaanInfo(
        @Field("IDMember") idMember: String
    ): Single<Response<MutableList<PendanaanInfo>>>

    @FormUrlEncoded
    @POST("DD_Kenaikan_limit.aspx")
    fun listNominalIncreaseLimit(
        @Field("IDMember") idMember: String
    ): Single<Response<MutableList<NominalIncreaseLimit>>>

    @FormUrlEncoded
    @POST("Box_Kenaikan_Limit.aspx")
    fun increaseLimitInfo(
        @Field("IDMember") idMember: String
    ): Single<Response<MutableList<IncreaseLimitInfo>>>

    @FormUrlEncoded
    @POST("Pendanaan_Pengajuan_kenaikan_limit.aspx")
    fun submitLimitIncrease(
        @Field("IDMember") idMember: String,
        @Field("LimitDiajukan") limit: String
    ): Single<Response<MutableList<BaseAPIResponse>>>

    @FormUrlEncoded
    @POST("Pendanaan_History_Pengembalian.aspx")
    fun billingHistory(
        @Field("IDMember") idMember: String
    ): Single<Response<MutableList<BillingTransaction>>>

    @FormUrlEncoded
    @POST("Pendanaan_Billing_Detail.aspx")
    fun billingCreditDetail(
        @Field("IDMember") idMember: String,
        @Field("IDBilling") idBilling: String
    ): Single<Response<MutableList<BillingVA>>>

    @FormUrlEncoded
    @POST("Pendanaan_Billing.aspx")
    fun billingCredit(
        @Field("IDMember") idMember: String
    ): Single<Response<MutableList<BillingCredit>>>

    @POST("FAQ.aspx")
    fun faq(): Single<Response<MutableList<Faq>>>

    @POST("SyaratKetentuan.aspx")
    fun termsConditions(): Single<Response<MutableList<TermsCondition>>>

    @FormUrlEncoded
    @POST("TOPUP_VA_Instruction.aspx")
    fun topupVAInstruction(
        @Field("IDMember") idMember: String,
        @Field("IDBankVA") idRequest: String
    ): Single<Response<MutableList<TopupInstruction>>>

    @Multipart
    @POST("Pendanaan_KUR_Document_SIUP.aspx")
    fun kurDocumentSIUP(
        @Part("IDMember") idMember: RequestBody,
        @Part("IDRequest") idRequest: RequestBody,
        @Part file: MultipartBody.Part
    ): Single<Response<MutableList<BaseAPIResponse>>>

    @Multipart
    @POST("Pendanaan_KUR_Document_NPWP.aspx")
    fun kurDocumentNPWP(
        @Part("IDMember") idMember: RequestBody,
        @Part("IDRequest") idRequest: RequestBody,
        @Part file: MultipartBody.Part
    ): Single<Response<MutableList<BaseAPIResponse>>>

    @Multipart
    @POST("Pendanaan_KUR_Document_SuratPengajuan.aspx")
    fun kurDocumentSurat(
        @Part("IDMember") idMember: RequestBody,
        @Part("IDRequest") idRequest: RequestBody,
        @Part file: MultipartBody.Part
    ): Single<Response<MutableList<BaseAPIResponse>>>

    @Multipart
    @POST("Pendanaan_KUR_Document_PhotoSelfie.aspx")
    fun kurDocumentSelfie(
        @Part("IDMember") idMember: RequestBody,
        @Part("IDRequest") idRequest: RequestBody,
        @Part file: MultipartBody.Part
    ): Single<Response<MutableList<BaseAPIResponse>>>

    @Multipart
    @POST("Pendanaan_KUR_Document_KK.aspx")
    fun kurDocumentKK(
        @Part("IDMember") idMember: RequestBody,
        @Part("IDRequest") idRequest: RequestBody,
        @Part file: MultipartBody.Part
    ): Single<Response<MutableList<BaseAPIResponse>>>

    @Multipart
    @POST("Pendanaan_KUR_Document_KTP.aspx")
    fun kurDocumentKTP(
        @Part("IDMember") idMember: RequestBody,
        @Part("IDRequest") idRequest: RequestBody,
        @Part file: MultipartBody.Part
    ): Single<Response<MutableList<BaseAPIResponse>>>

    @FormUrlEncoded
    @POST("Pendanaan_KUR_Fulfillment.aspx")
    fun fulfillmentKUR(
        @Field("IDMember") idmember: String,
        @Field("IDRequest") idRequest: String,
        @Field("Kewarganegaraan") kewarganegaraan: String,
        @Field("telpHP") phone: String,
        @Field("FaxArea") faxArea: String,
        @Field("Fax") fax: String,
        @Field("Profesi") profesi: String,
        @Field("Jabatan") jabatan: String,
        @Field("BidangUsaha") bidangUsaha: String,
        @Field("BerdiriSejak") berdiriSejak: String,
        @Field("BekerjaUsahaSejak") bekerjaSejak: String,
        @Field("TempatBekerja") tempatBekerja: String,
        @Field("NamaPerusahaan") namaPerusahaan: String,
        @Field("AlamatKantorLine1") alamatKantor1: String,
        @Field("AlamatKantorLine2") alamatKantor2: String,
        @Field("AlamatKantorLine3") alamatKantor3: String,
        @Field("KecamatanKantor") kecamatanKantor: String,
        @Field("KelurahanKantor") kelurahanKantor: String,
        @Field("KodePosKantor") kodePosKantor: String,
        @Field("NoFaxAreaKantor") faxAreaKantor: String,
        @Field("NoFaxKantor") faxKantor: String,
        @Field("NoTelpAreaKantor") telpAreaKantor: String,
        @Field("NoTelpKantor") telpKantor: String,
        @Field("NamaEmergency") emergencyName: String,
        @Field("Hubungan") hubungan: String,
        @Field("ProfesiPasangan") profesiPasangan: String = "",
        @Field("TempatBekerjaPasangan") tempatBekerjaPasangan: String = "",
        @Field("BidangUsahaPasangan") bidangUsahaPasangan: String = "",
        @Field("BekerjaTanggalMenikah") tanggalMenikah: String,
        @Field("LokasiDatiIIUsaha") datill: String,
        @Field("SumberDana") sumberDana: String,
        @Field("Komoditas") komoditas: String,
        @Field("LuasLahan") luasLahan: String,
        @Field("JenisDebitur") jenisDebitur: String,
        @Field("NoSuratPermohonan") suratPermhononan: String,
        @Field("NoIjinUsaha") ijinUsaha: String
    ): Single<Response<MutableList<KUMFulfillmentResponse>>>

    @POST("DDM_JenisKredit_KUR.aspx")
    fun formJenisKreditKUR(): Single<Response<MutableList<JenisKredit>>>

    @FormUrlEncoded
    @POST("Pendanaan_KUR_PreScreening.aspx")
    fun preScreeningKUR(
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
        @Field("NPWP") npwp: String,
        @Field("NomorRekening") noRek: String
    ): Single<Response<MutableList<KURPrescreeningResponse>>>

    @FormUrlEncoded
    @POST("TOPUP_KirimKredit_Step2.aspx")
    fun submitTopupViaKredit(
        @Field("IDMember") idmember: String,
        @Field("Jumlah") amount: String
    ): Single<Response<MutableList<TopupViaKreditResultResponse>>>

    @FormUrlEncoded
    @POST("TOPUP_KirimKredit_Step1.aspx")
    fun topupViaKreditStat(
        @Field("IDMember") idmember: String
    ): Single<Response<MutableList<TopupViaKreditStatResponse>>>

    @FormUrlEncoded
    @POST("Pendanaan_LIST.aspx")
    fun listPendanaanReq(
        @Field("IDMember") idmember: String
    ): Single<Response<MutableList<PendanaanItem>>>

    @FormUrlEncoded
    @POST("Pendanaan_SubmitFINAL.aspx")
    fun submitFinalCredit(
        @Field("IDMember") idmember: String,
        @Field("IDRequest") idRequest: String
    ): Single<Response<MutableList<BaseAPIResponse>>>

    @FormUrlEncoded
    @POST("Pendanaan_SyaratKetentuan.aspx")
    fun creditTerms(
        @Field("IDMember") idmember: String,
        @Field("IDRequest") idRequest: String
    ): Single<Response<MutableList<TermsResponse>>>

    @Multipart
    @POST("Pendanaan_KUM_Document_NPWP.aspx")
    fun kumDocumentNPWP(
        @Part("IDMember") idMember: RequestBody,
        @Part("IDRequest") idRequest: RequestBody,
        @Part file: MultipartBody.Part
    ): Single<Response<MutableList<BaseAPIResponse>>>

    @Multipart
    @POST("Pendanaan_KUM_Document_SuratPengajuan.aspx")
    fun kumDocumentSurat(
        @Part("IDMember") idMember: RequestBody,
        @Part("IDRequest") idRequest: RequestBody,
        @Part file: MultipartBody.Part
    ): Single<Response<MutableList<BaseAPIResponse>>>

    @Multipart
    @POST("Pendanaan_KUM_Document_PhotoSelfie.aspx")
    fun kumDocumentSelfie(
        @Part("IDMember") idMember: RequestBody,
        @Part("IDRequest") idRequest: RequestBody,
        @Part file: MultipartBody.Part
    ): Single<Response<MutableList<BaseAPIResponse>>>

    @Multipart
    @POST("Pendanaan_KUM_Document_KK.aspx")
    fun kumDocumentKK(
        @Part("IDMember") idMember: RequestBody,
        @Part("IDRequest") idRequest: RequestBody,
        @Part file: MultipartBody.Part
    ): Single<Response<MutableList<BaseAPIResponse>>>

    @Multipart
    @POST("Pendanaan_KUM_Document_KTP.aspx")
    fun kumDocumentKTP(
        @Part("IDMember") idMember: RequestBody,
        @Part("IDRequest") idRequest: RequestBody,
        @Part file: MultipartBody.Part
    ): Single<Response<MutableList<BaseAPIResponse>>>

    @FormUrlEncoded
    @POST("Pendanaan_KUM_Fulfillment.aspx")
    fun fulfillmentKUM(
        @Field("IDMember") idmember: String,
        @Field("IDRequest") idRequest: String,
        @Field("Kewarganegaraan") kewarganegaraan: String,
        @Field("telpHP") phone: String,
        @Field("FaxArea") faxArea: String,
        @Field("Fax") fax: String,
        @Field("Profesi") profesi: String,
        @Field("Jabatan") jabatan: String,
        @Field("BidangUsaha") bidangUsaha: String,
        @Field("BerdiriSejak") berdiriSejak: String,
        @Field("BekerjaUsahaSejak") bekerjaSejak: String,
        @Field("TempatBekerja") tempatBekerja: String,
        @Field("NamaPerusahaan") namaPerusahaan: String,
        @Field("AlamatKantorLine1") alamatKantor1: String,
        @Field("AlamatKantorLine2") alamatKantor2: String,
        @Field("AlamatKantorLine3") alamatKantor3: String,
        @Field("KecamatanKantor") kecamatanKantor: String,
        @Field("KelurahanKantor") kelurahanKantor: String,
        @Field("KodePosKantor") kodePosKantor: String,
        @Field("NoFaxAreaKantor") faxAreaKantor: String,
        @Field("NoFaxKantor") faxKantor: String,
        @Field("NoTelpAreaKantor") telpAreaKantor: String,
        @Field("NoTelpKantor") telpKantor: String,
        @Field("NamaEmergency") emergencyName: String,
        @Field("Hubungan") hubungan: String,
        @Field("ProfesiPasangan") profesiPasangan: String = "",
        @Field("TempatBekerjaPasangan") tempatBekerjaPasangan: String = "",
        @Field("BidangUsahaPasangan") bidangUsahaPasangan: String = "",
        @Field("BekerjaTanggalMenikah") tanggalMenikah: String,
        @Field("LokasiDatiIIUsaha") datill: String,
        @Field("SumberDana") sumberDana: String,
        @Field("Komoditas") komoditas: String,
        @Field("LuasLahan") luasLahan: String,
        @Field("JenisDebitur") jenisDebitur: String,
    ): Single<Response<MutableList<KUMFulfillmentResponse>>>

    @POST("DDM_JenisDebitur.aspx")
    fun formJenisDebitur(): Single<Response<MutableList<JenisDebitur>>>

    @POST("DDM_Komoditas.aspx")
    fun formKomoditas(): Single<Response<MutableList<Komoditas>>>

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

    @FormUrlEncoded
    @POST("DDM_LokasiDatiII.aspx")
    fun formlokasiDatill(
        @Field("keyword") keyword: String
    ): Single<Response<MutableList<LokasiDatill>>>

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
    @POST("HISTORY_Transaksi_ALL.aspx")
    fun transactionHistoryAll(
        @Field("IDMember") idMember: String
    ): Single<Response<MutableList<TransactionItem>>>

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
        @Field("NamaLengkap") fullName: String,
        @Field("NIK") nik: String
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
