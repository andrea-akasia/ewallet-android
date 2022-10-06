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
import com.mobile.ewallet.model.api.dashboard.DashboardBalance
import com.mobile.ewallet.model.api.dashboard.TransactionItem
import com.mobile.ewallet.model.api.detailpokemon.DetailPokemonResponse
import com.mobile.ewallet.model.api.moneyrequest.MoneyRequestData
import com.mobile.ewallet.model.api.profile.ProfileAPIResponse
import com.mobile.ewallet.model.api.register.ConfirmOTPAPIResponse
import com.mobile.ewallet.model.api.sendmoney.byscan.AdminFeeResponse
import com.mobile.ewallet.model.api.sendmoney.byscan.MinimumNominalResponse
import com.mobile.ewallet.model.api.sendmoney.byscan.SendMoneyResult
import com.mobile.ewallet.model.api.sendmoney.byscan.TransactionDetail
import com.mobile.ewallet.model.api.splashscreen.SplashscreenAPIResponse
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

    fun getIdMember(): String = prefs.getString(KEY_ID_MEMBER, "")!!

    fun setLoginState(isLoggedIn: Boolean) { prefs.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn) }

    fun getLoginState(): Boolean = prefs.getBoolean(KEY_IS_LOGGED_IN)

    fun clearPrefs(){
        prefs.putBoolean(KEY_IS_LOGGED_IN, false)
        prefs.putString(KEY_ID_MEMBER, "")
    }


    /* ---------------------------------------- Network ----------------------------------------- */
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

    fun saveProfile(name: String, phone: String, birthDate: String = "", nik: String = "")
    : Single<Response<MutableList<BaseAPIResponse>>> {
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

    fun finishRegister(phone: String, fullName: String): Single<Response<MutableList<BaseAPIResponse>>> {
        return api.finishRegister(phone, getIdMember(), fullName)
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