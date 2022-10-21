package com.mobile.ewallet.data

import com.mobile.ewallet.data.local.pokemon.LocalPokemon
import com.mobile.ewallet.network.APIService
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Singleton
import javax.inject.Inject
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import androidx.paging.DataSource
import com.mobile.ewallet.model.api.BaseAPIResponse
import com.mobile.ewallet.model.api.badge.Badge
import com.mobile.ewallet.model.api.badge.BadgeStatus
import com.mobile.ewallet.model.api.credit.*
import com.mobile.ewallet.model.api.credit.billing.BillingCredit
import com.mobile.ewallet.model.api.credit.billing.BillingTransaction
import com.mobile.ewallet.model.api.credit.billing.BillingVA
import com.mobile.ewallet.model.api.dashboard.DashboardBalance
import com.mobile.ewallet.model.api.dashboard.TransactionItem
import com.mobile.ewallet.model.api.detailpokemon.DetailPokemonResponse
import com.mobile.ewallet.model.api.moneyrequest.MoneyRequestData
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
import com.mobile.ewallet.util.Constant.Companion.KEY_ID_MEMBER
import com.mobile.ewallet.util.Constant.Companion.KEY_IS_LOGGED_IN
import com.mobile.ewallet.util.createStringReqBody
import okhttp3.MultipartBody


@Singleton
class DataManager
@Inject constructor(
    private val api: APIService,
    private val prefs: PreferencesHelper,
    private val localDatabase: AppDatabase){

    /* ---------------------------------------- SQLite ------------------------------------------ */

    fun saveAllPokemonToLocal(listPokemon: List<LocalPokemon>): Completable {
        return Completable.fromAction {
            localDatabase.PokemonDao().saveAllPokemon(listPokemon)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun loadAllPokemonFromLocal(): DataSource.Factory<Int, LocalPokemon> {
        return localDatabase.PokemonDao().loadAllPokemonPaged()
    }

    /* -------------------------------------- Preferences --------------------------------------- */

    fun setIdMember(username: String) { prefs.putString(KEY_ID_MEMBER, username) }

    fun getIdMember(): String = /*"22092314153995866"*/prefs.getString(KEY_ID_MEMBER, "")!!

    fun setLoginState(isLoggedIn: Boolean) { prefs.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn) }

    fun getLoginState(): Boolean = /*true*/prefs.getBoolean(KEY_IS_LOGGED_IN)

    fun clearPrefs(){
        prefs.putBoolean(KEY_IS_LOGGED_IN, false)
        prefs.putString(KEY_ID_MEMBER, "")
    }

    /* ---------------------------------------- Network ----------------------------------------- */
    fun listNominalIncreaseLimit(): Single<Response<MutableList<NominalIncreaseLimit>>> {
        return api.listNominalIncreaseLimit(getIdMember())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun increaseLimitInfo(): Single<Response<MutableList<IncreaseLimitInfo>>> {
        return api.increaseLimitInfo(getIdMember())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun submitLimitIncrease(limit: String): Single<Response<MutableList<BaseAPIResponse>>> {
        return api.submitLimitIncrease(getIdMember(), limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun billingHistory(): Single<Response<MutableList<BillingTransaction>>> {
        return api.billingHistory(getIdMember())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun billingDetail(idBilling: String): Single<Response<MutableList<BillingVA>>> {
        return api.billingCreditDetail(getIdMember(), idBilling)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun billingCredit(): Single<Response<MutableList<BillingCredit>>> {
        return api.billingCredit(getIdMember())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun faq(): Single<Response<MutableList<Faq>>> {
        return api.faq()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun termsConditions(): Single<Response<MutableList<TermsCondition>>> {
        return api.termsConditions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun topupVAInstruction(idbank: String): Single<Response<MutableList<TopupInstruction>>> {
        return api.topupVAInstruction(getIdMember(), idbank)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun kurDocumentSIUP(idRequest: String, file: MultipartBody.Part): Single<Response<MutableList<BaseAPIResponse>>> {
        return api.kurDocumentSIUP(createStringReqBody(getIdMember()), createStringReqBody(idRequest), file)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun kurDocumentSurat(idRequest: String, file: MultipartBody.Part): Single<Response<MutableList<BaseAPIResponse>>> {
        return api.kurDocumentSurat(createStringReqBody(getIdMember()), createStringReqBody(idRequest), file)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun kurDocumentSelfie(idRequest: String, file: MultipartBody.Part): Single<Response<MutableList<BaseAPIResponse>>> {
        return api.kurDocumentSelfie(createStringReqBody(getIdMember()), createStringReqBody(idRequest), file)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun kurDocumentKK(idRequest: String, file: MultipartBody.Part): Single<Response<MutableList<BaseAPIResponse>>> {
        return api.kurDocumentKK(createStringReqBody(getIdMember()), createStringReqBody(idRequest), file)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun kurDocumentKTP(idRequest: String, file: MultipartBody.Part): Single<Response<MutableList<BaseAPIResponse>>> {
        return api.kurDocumentKTP(createStringReqBody(getIdMember()), createStringReqBody(idRequest), file)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun kurDocumentNPWP(idRequest: String, file: MultipartBody.Part): Single<Response<MutableList<BaseAPIResponse>>> {
        return api.kurDocumentNPWP(createStringReqBody(getIdMember()), createStringReqBody(idRequest), file)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun submitFulfillmentKUR(data: KURFulfillmentBody): Single<Response<MutableList<KUMFulfillmentResponse>>> {
        return api.fulfillmentKUR(
            idmember = getIdMember(),
            idRequest = data.idRequest,
            kewarganegaraan = data.codeKewarganegaraan,
            phone = data.nomorPonsel,
            faxArea = data.faxArea,
            fax = data.fax,
            profesi = data.codeProfesi,
            jabatan = data.codeJabatan,
            bidangUsaha = data.codeBidangUsaha,
            berdiriSejak = data.berdiriSejak,
            bekerjaSejak = data.bekerjaSejak,
            tempatBekerja = data.codeTempatBekerja,
            namaPerusahaan = data.namaPerusahaan,
            alamatKantor1 = data.alamatKantor1,
            alamatKantor2 = data.alamatKantor2,
            alamatKantor3 = data.alamatKantor3,
            kecamatanKantor = data.kecamatanKantor,
            kelurahanKantor = data.kelurahanKantor,
            kodePosKantor = data.codeKodePosKantor,
            faxAreaKantor = data.faxAreaKantor,
            faxKantor = data.faxKantor,
            telpAreaKantor = data.telpAreaKantor,
            telpKantor = data.telpKantor,
            emergencyName = data.kontakDarurat,
            hubungan = data.hubungan,
            profesiPasangan = data.codeProfesiPasangan,
            tempatBekerjaPasangan = data.codeTempatBekerjaPasangan,
            bidangUsahaPasangan = data.codeBidangUsahaPasangan,
            tanggalMenikah = data.tanggalMenikah,
            datill = data.codeDatill,
            sumberDana = data.codeSumberDana,
            komoditas = data.codeKomoditas,
            luasLahan = data.luasLahan,
            jenisDebitur = data.codeJenisDebitur,
            suratPermhononan = data.suratPermohonan,
            ijinUsaha = data.ijinUsaha
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun formJenisKreditKUR(): Single<Response<MutableList<JenisKredit>>> {
        return api.formJenisKreditKUR()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun submitPrescreeningKUR(data: KURPrescreeningBody): Single<Response<MutableList<KURPrescreeningResponse>>> {
        return api.preScreeningKUR(
            idmember = getIdMember(),
            idRequest = data.idRequest,
            namaPelapor = data.namaPelapor,
            nomorKK = data.nomorKK,
            tempatLahir = data.tempatLahir,
            jenisKelamin = data.codeJenisKelamin,
            tanggalLahir = data.tanggalLahir,
            pendidikanTerakhir = data.codePendidikan,
            namaIbu = data.namaIbu,
            telpArea = data.noTelpArea,
            telp = data.noTelp,
            nomorKTP = data.nomorKTP,
            namaKTP = data.namaKTP,
            alamatKTP = data.alamatKTP,
            kotaKTP = data.kotaKTP,
            kecamatanKTP = data.kecamatanKTP,
            kelurahanKTP = data.kelurahanKTP,
            kodePosKTP = data.codeKodePosKTP,
            alamatRumah = data.alamatRumah,
            kotaRumah = data.kotaRumah,
            kecamatanRumah = data.kecamatanRumah,
            kelurahanRumah = data.kelurahanRumah,
            kodePosRumah = data.codeKodePosRumah,
            datillRumah = data.codeDatillRumah,
            statusRumah = data.codeStatusRumah,
            tanggalMenempatiRumah = data.tanggalMulaiMenempatiRumah,
            statusPernikahan = data.codeStatusPernikahan,
            namaPasangan = data.namaPasangan,
            tanggalLahirPasangan = data.tanggalLahirPasangan,
            nomorKTPPasangan = data.nomorKTPPasangan,
            jenisKredit = data.codeJenisKredit,
            limitAwal = data.limitAwal,
            jangkaWaktu = data.codeJangkaWaktu,
            npwp = data.npwp,
            noRek = data.noRek
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun submitTopupViaKredit(amount: String): Single<Response<MutableList<TopupViaKreditResultResponse>>> {
        return api.submitTopupViaKredit(getIdMember(), amount)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun topupViaKreditStat(): Single<Response<MutableList<TopupViaKreditStatResponse>>> {
        return api.topupViaKreditStat(getIdMember())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun listPendanaanReq(): Single<Response<MutableList<PendanaanItem>>> {
        return api.listPendanaanReq(getIdMember())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun submitFinalCredit(idRequest: String): Single<Response<MutableList<BaseAPIResponse>>> {
        return api.submitFinalCredit(getIdMember(), idRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun creditTerms(idRequest: String): Single<Response<MutableList<TermsResponse>>> {
        return api.creditTerms(getIdMember(), idRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun kumDocumentSurat(idRequest: String, file: MultipartBody.Part): Single<Response<MutableList<BaseAPIResponse>>> {
        return api.kumDocumentSurat(createStringReqBody(getIdMember()), createStringReqBody(idRequest), file)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun kumDocumentNPWP(idRequest: String, file: MultipartBody.Part): Single<Response<MutableList<BaseAPIResponse>>> {
        return api.kumDocumentNPWP(createStringReqBody(getIdMember()), createStringReqBody(idRequest), file)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun kumDocumentSelfie(idRequest: String, file: MultipartBody.Part): Single<Response<MutableList<BaseAPIResponse>>> {
        return api.kumDocumentSelfie(createStringReqBody(getIdMember()), createStringReqBody(idRequest), file)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun kumDocumentKK(idRequest: String, file: MultipartBody.Part): Single<Response<MutableList<BaseAPIResponse>>> {
        return api.kumDocumentKK(createStringReqBody(getIdMember()), createStringReqBody(idRequest), file)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun kumDocumentKTP(idRequest: String, file: MultipartBody.Part): Single<Response<MutableList<BaseAPIResponse>>> {
        return api.kumDocumentKTP(createStringReqBody(getIdMember()), createStringReqBody(idRequest), file)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun submitFulfillmentKUM(data: KUMFulfillmentBody): Single<Response<MutableList<KUMFulfillmentResponse>>> {
        return api.fulfillmentKUM(
            idmember = getIdMember(),
            idRequest = data.idRequest,
            kewarganegaraan = data.codeKewarganegaraan,
            phone = data.nomorPonsel,
            faxArea = data.faxArea,
            fax = data.fax,
            profesi = data.codeProfesi,
            jabatan = data.codeJabatan,
            bidangUsaha = data.codeBidangUsaha,
            berdiriSejak = data.berdiriSejak,
            bekerjaSejak = data.bekerjaSejak,
            tempatBekerja = data.codeTempatBekerja,
            namaPerusahaan = data.namaPerusahaan,
            alamatKantor1 = data.alamatKantor1,
            alamatKantor2 = data.alamatKantor2,
            alamatKantor3 = data.alamatKantor3,
            kecamatanKantor = data.kecamatanKantor,
            kelurahanKantor = data.kelurahanKantor,
            kodePosKantor = data.codeKodePosKantor,
            faxAreaKantor = data.faxAreaKantor,
            faxKantor = data.faxKantor,
            telpAreaKantor = data.telpAreaKantor,
            telpKantor = data.telpKantor,
            emergencyName = data.kontakDarurat,
            hubungan = data.hubungan,
            profesiPasangan = data.codeProfesiPasangan,
            tempatBekerjaPasangan = data.codeTempatBekerjaPasangan,
            bidangUsahaPasangan = data.codeBidangUsahaPasangan,
            tanggalMenikah = data.tanggalMenikah,
            datill = data.codeDatill,
            sumberDana = data.codeSumberDana,
            komoditas = data.codeKomoditas,
            luasLahan = data.luasLahan,
            jenisDebitur = data.codeJenisDebitur
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun formJenisDebitur(): Single<Response<MutableList<JenisDebitur>>> {
        return api.formJenisDebitur()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun formKomoditas(): Single<Response<MutableList<Komoditas>>> {
        return api.formKomoditas()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun formSumberDana(): Single<Response<MutableList<SumberDana>>> {
        return api.formSumberDana()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun formTempatBekerja(): Single<Response<MutableList<TempatBekerja>>> {
        return api.formTempatBekerja()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun formBidangUsaha(): Single<Response<MutableList<BidangUsaha>>> {
        return api.formBidangUsaha()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun formJabatan(): Single<Response<MutableList<Jabatan>>> {
        return api.formJabatan()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun formProfesi(): Single<Response<MutableList<Profesi>>> {
        return api.formProfesi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun formKewarganegaraan(): Single<Response<MutableList<Kewarganegaraan>>> {
        return api.formKewarganegaraan()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun submitPrescreeningKUM(data: KUMPrescreeningBody): Single<Response<MutableList<KUMPrescreeningResponse>>> {
        return api.preScreeningKUM(
            idmember = getIdMember(),
            idRequest = data.idRequest,
            namaPelapor = data.namaPelapor,
            nomorKK = data.nomorKK,
            tempatLahir = data.tempatLahir,
            jenisKelamin = data.codeJenisKelamin,
            tanggalLahir = data.tanggalLahir,
            pendidikanTerakhir = data.codePendidikan,
            namaIbu = data.namaIbu,
            telpArea = data.noTelpArea,
            telp = data.noTelp,
            nomorKTP = data.nomorKTP,
            namaKTP = data.namaKTP,
            alamatKTP = data.alamatKTP,
            kotaKTP = data.kotaKTP,
            kecamatanKTP = data.kecamatanKTP,
            kelurahanKTP = data.kelurahanKTP,
            kodePosKTP = data.codeKodePosKTP,
            alamatRumah = data.alamatRumah,
            kotaRumah = data.kotaRumah,
            kecamatanRumah = data.kecamatanRumah,
            kelurahanRumah = data.kelurahanRumah,
            kodePosRumah = data.codeKodePosRumah,
            datillRumah = data.codeDatillRumah,
            statusRumah = data.codeStatusRumah,
            tanggalMenempatiRumah = data.tanggalMulaiMenempatiRumah,
            statusPernikahan = data.codeStatusPernikahan,
            namaPasangan = data.namaPasangan,
            tanggalLahirPasangan = data.tanggalLahirPasangan,
            nomorKTPPasangan = data.nomorKTPPasangan,
            jenisKredit = data.codeJenisKredit,
            limitAwal = data.limitAwal,
            jangkaWaktu = data.codeJangkaWaktu,
            npwp = data.npwp
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun generateCreditRequest(): Single<Response<MutableList<GeneratedCreditRequestId>>> {
        return api.generateCreditRequest(getIdMember())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun formJangkaWaktu(jenisKredit: String): Single<Response<MutableList<JangkaWaktu>>> {
        return api.formJangkaWaktu(jenisKredit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadJenisKreditParameter(jenisKredit: String): Single<Response<MutableList<JenisKreditParameter>>> {
        return api.jenisKreditParameter(jenisKredit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun formJenisKreditKUM(): Single<Response<MutableList<JenisKredit>>> {
        return api.formJenisKreditKUM()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun formStatusPernikahan(): Single<Response<MutableList<StatusPernikahan>>> {
        return api.formStatusPernikahan()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun formStatusRumah(): Single<Response<MutableList<StatusRumah>>> {
        return api.formStatusRumah()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun formLokasiDatill(keyword: String): Single<Response<MutableList<LokasiDatill>>> {
        return api.formlokasiDatill(keyword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun formKodePos(keyword: String): Single<Response<MutableList<KodePos>>> {
        return api.formKodePos(keyword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun formPendidikanTerakhir(): Single<Response<MutableList<Pendidikan>>> {
        return api.formPendidikanTerakhir()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun formJenisKelamin(): Single<Response<MutableList<JenisKelamin>>> {
        return api.formJenisKelamin()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadListVirtualAcc(): Single<Response<MutableList<TopupVA>>> {
        return api.listVirtualAccount(getIdMember())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadTransactionDetailContact(id: String): Single<Response<MutableList<TransactionDetailContact>>> {
        return api.contactTransactionDetail(getIdMember(), id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun contactSendMoney(idMemberDestination: String, amount: String, adminFee: String, total: String): Single<Response<MutableList<SendMoneyContactResult>>> {
        return api.contactSendMoney(getIdMember(), idMemberDestination, amount, adminFee, total)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun contactLoadAdminFee(idMemberDestination: String, amount: String): Single<Response<MutableList<AdminFeeResponse>>> {
        return api.contactAdminFee(getIdMember(), idMemberDestination, amount)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun contactLoadMinimumNominal(idMemberDestination: String): Single<Response<MutableList<MinimumNominalContactResponse>>> {
        return api.contactLoadNominal(getIdMember(), idMemberDestination)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadEwalletUser(): Single<Response<MutableList<ContactUser>>> {
        return api.contacts(getIdMember())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadTransactionDetailTransfer(id: String): Single<Response<MutableList<TransactionDetailTransfer>>> {
        return api.transferTransactionDetail(getIdMember(), id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun transferSendMoney(
        idBank: String,
        accountNumber: String,
        name: String,
        amount: String,
        adminFee: String,
        total: String
    ): Single<Response<MutableList<SendMoneyResultTrfResponse>>> {
        return api.trfSendMoney(getIdMember(), idBank, accountNumber, name, amount, adminFee, total)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun transferLoadAdminFee(idBank: String, accountNumber: String, amount: String): Single<Response<MutableList<AdminFeeTrfResponse>>> {
        return api.trfLoadAdminFee(getIdMember(), idBank, accountNumber, amount)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun transferLoadMinimumNominal(idBank: String, accountNumber: String): Single<Response<MutableList<MinimumNominalTrfResponse>>> {
        return api.trfLoadNominal(getIdMember(), idBank, accountNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadBankList(): Single<Response<MutableList<Bank>>> {
        return api.listBank()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadHistoryTransferTransaction(): Single<Response<MutableList<HistoryTransferTransaction>>> {
        return api.transferTransactionHistory(getIdMember())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadTransactionDetail(id: String): Single<Response<MutableList<TransactionDetail>>> {
        return api.transactionDetail(getIdMember(), id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun scanSendMoney(qr: String, amount: String, adminFee: String, total: String): Single<Response<MutableList<SendMoneyResult>>> {
        return api.scanSendMoney(getIdMember(), qr, amount, adminFee, total)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun scanLoadAdminFee(qr: String, amount: String): Single<Response<MutableList<AdminFeeResponse>>> {
        return api.scanAdminFee(getIdMember(), qr, amount)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun scanLoadMinimumNominal(qr: String): Single<Response<MutableList<MinimumNominalResponse>>> {
        return api.scanLoadNominal(getIdMember(), qr)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadMoneyRequest(): Single<Response<MutableList<MoneyRequestData>>> {
        return api.moneyRequest(getIdMember())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadCreditHistoryTransaction(): Single<Response<MutableList<TransactionItem>>> {
        return api.creditTransactionHistory(getIdMember())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadBadgeStatus(): Single<Response<MutableList<BadgeStatus>>> {
        return api.statusBadge(getIdMember())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadListBadge(): Single<Response<MutableList<Badge>>> {
        return api.listBadge(getIdMember())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadAllHistoryTransaction(): Single<Response<MutableList<TransactionItem>>> {
        return api.transactionHistoryAll(getIdMember())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadHistoryTransaction(): Single<Response<MutableList<TransactionItem>>> {
        return api.transactionHistory(getIdMember())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadDashboardBalance(): Single<Response<MutableList<DashboardBalance>>> {
        return api.dashboardBalance(getIdMember())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun saveProfile(name: String, phone: String, birthDate: String = "", nik: String = ""): Single<Response<MutableList<BaseAPIResponse>>> {
        return api.saveProfile(getIdMember(), phone, name, birthDate, nik)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun uploadPhotoProfile(image: MultipartBody.Part): Single<Response<MutableList<BaseAPIResponse>>> {
        return api.uploadPhotoProfile(createStringReqBody(getIdMember()), image)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadUserProfile(): Single<Response<MutableList<ProfileAPIResponse>>> {
        return api.loadUserProfile(getIdMember())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun confirmOTPLogin(phone: String, uuid: String = "", otp: String): Single<Response<MutableList<ConfirmOTPAPIResponse>>> {
        return api.confirmOtpLogin(phone, uuid, otp)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun resendOTPLogin(phone: String, uuid: String = ""): Single<Response<MutableList<BaseAPIResponse>>> {
        return api.resendOTPLogin(phone, uuid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun reqOTPLogin(phone: String, uuid: String = ""): Single<Response<MutableList<BaseAPIResponse>>> {
        return api.reqOTPLogin(phone, uuid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun finishRegister(phone: String, fullName: String, nik: String): Single<Response<MutableList<BaseAPIResponse>>> {
        return api.finishRegister(phone, getIdMember(), fullName, nik)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun confirmOTPRegister(phone: String, uuid: String, otp: String): Single<Response<MutableList<ConfirmOTPAPIResponse>>> {
        return api.confirmOtpRegister(phone, uuid, otp)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun requestResendOTPRegister(phone: String, uuid: String): Single<Response<MutableList<BaseAPIResponse>>> {
        return api.reqResendOTPRegister(phone, uuid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun requestOTPRegister(phone: String, uuid: String): Single<Response<MutableList<BaseAPIResponse>>> {
        return api.reqOTPRegister(phone, uuid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun splashscreen(): Single<Response<MutableList<SplashscreenAPIResponse>>> {
        return api.splashscreen()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    suspend fun reqPokemon(page: Int, limit: Int) = api.requestListPokemon(limit, page)

    fun reqDetailPokemon(name: String): Single<Response<DetailPokemonResponse>> {
        return api.requestDetailPokemon(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}