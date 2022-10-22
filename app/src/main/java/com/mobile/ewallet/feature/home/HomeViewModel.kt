package com.mobile.ewallet.feature.home

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.model.api.badge.Badge
import com.mobile.ewallet.model.api.badge.BadgeStatus
import com.mobile.ewallet.model.api.credit.PendanaanInfo
import com.mobile.ewallet.model.api.dashboard.DashboardBalance
import com.mobile.ewallet.model.api.dashboard.TransactionItem
import com.mobile.ewallet.model.api.profile.ProfileAPIResponse
import com.mobile.ewallet.model.api.sendmoney.byscan.TransactionDetail
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
class HomeViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel(){

    internal var onProfileLoaded = MutableLiveData<ProfileAPIResponse>()
    internal var warningMessage = MutableLiveData<String>()
    internal var onDashboardBalanceLoaded = MutableLiveData<DashboardBalance>()
    internal var onHistoryTransactionLoaded = MutableLiveData<MutableList<TransactionItem>>()
    internal var onBadgesLoaded = MutableLiveData<MutableList<Badge>>()
    internal var onBadgeStatusLoaded = MutableLiveData<BadgeStatus>()
    internal var onTransactionDetailLoaded = MutableLiveData<TransactionDetail>()
    internal var onAllHistoryTransactionLoaded = MutableLiveData<MutableList<TransactionItem>>()
    internal var onApplyCreditInfoLoaded = MutableLiveData<PendanaanInfo>()

    var balanceData: DashboardBalance? = null
    var pendanaanInfo: PendanaanInfo? = null

    fun loadPendanaanInfo() {
        pendanaanInfo = null
        dataManager.pendanaanInfo()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                pendanaanInfo = response[0]
                                onApplyCreditInfoLoaded.postValue(
                                    pendanaanInfo?.apply {
                                        /*mode = "1B"
                                        title = "Sedang Diproses!"
                                        subMessage = "Detail Pengajuan"*/
                                    }
                                )
                            }
                        }
                    } else {
                        // not 20x
                        val code = res.code()
                        Timber.w(Throwable("Server Error $code, ${res.message()}"))
                        warningMessage.postValue("load pendanaan info failed, Server Error $code")
                    }
                },
                { err ->
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

    fun loadContactTransactionDetail(id: String) {
        dataManager.loadTransactionDetailContact(id)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                onTransactionDetailLoaded.postValue(
                                    TransactionDetail(
                                        idTransaction = response[0].iDTransaksi,
                                        transactionType = response[0].typeKirimUang,
                                        metodeBayar = response[0].metodeBayar,
                                        reffNumber = response[0].nomorReferensi,
                                        time = response[0].waktuTransaksi,
                                        amount = response[0].jumlah,
                                        adminFeeText = response[0].biayaAdmin,
                                        total = response[0].totalPembayaran,
                                        name = response[0].namaBeneficiary,
                                        phone = response[0].phoneBeneficiary
                                    ).apply {
                                        destinationName = response[0].namaBeneficiary
                                        destinationPhone = response[0].phoneBeneficiary
                                        destinationPhoto = response[0].photo
                                    }
                                )
                            }else{
                                warningMessage.postValue("empty data")
                            }
                        }
                    } else {
                        // not 20x
                        val code = res.code()
                        Timber.w(Throwable("Server Error $code, ${res.message()}"))
                        warningMessage.postValue(res.message())
                    }
                },
                { err ->
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

    fun loadTransferTransactionDetail(id: String) {
        dataManager.loadTransactionDetailTransfer(id)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                onTransactionDetailLoaded.postValue(
                                    TransactionDetail(
                                        idTransaction = response[0].iDTransaksi,
                                        transactionType = response[0].typeKirimUang,
                                        metodeBayar = response[0].metodeBayar,
                                        reffNumber = response[0].nomorReferensi,
                                        time = response[0].waktuTransaksi,
                                        amount = response[0].jumlah,
                                        adminFeeText = response[0].biayaAdmin,
                                        total = response[0].totalPembayaran,
                                        name = response[0].name,
                                        phone = "${response[0].norek} (${response[0].bank})"
                                    )
                                )
                            }else{
                                warningMessage.postValue("empty data")
                            }
                        }
                    } else {
                        // not 20x
                        val code = res.code()
                        Timber.w(Throwable("Server Error $code, ${res.message()}"))
                        warningMessage.postValue(res.message())
                    }
                },
                { err ->
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

    fun loadTransactionDetail(id: String) {
        dataManager.loadTransactionDetail(id)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                onTransactionDetailLoaded.postValue(response[0])
                            }else{
                                warningMessage.postValue("empty data")
                            }
                        }
                    } else {
                        // not 20x
                        val code = res.code()
                        Timber.w(Throwable("Server Error $code, ${res.message()}"))
                        warningMessage.postValue(res.message())
                    }
                },
                { err ->
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

    fun loadBadgeStatus() {
        dataManager.loadBadgeStatus()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                onBadgeStatusLoaded.postValue(response[0])
                            }else{
                                warningMessage.postValue("empty data badge box")
                            }
                        }
                    } else {
                        // not 20x
                        val code = res.code()
                        Timber.w(Throwable("Server Error $code, ${res.message()}"))
                        warningMessage.postValue(res.message())
                    }
                },
                { err ->
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

    fun loadBadges() {
        dataManager.loadListBadge()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            onBadgesLoaded.postValue(response)
                        }
                    } else {
                        // not 20x
                        val code = res.code()
                        Timber.w(Throwable("Server Error $code, ${res.message()}"))
                        warningMessage.postValue(res.message())
                    }
                },
                { err ->
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

    fun loadAllHistoryTransaction() {
        dataManager.loadAllHistoryTransaction()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            onAllHistoryTransactionLoaded.postValue(response)
                        }
                    } else {
                        // not 20x
                        val code = res.code()
                        Timber.w(Throwable("Server Error $code, ${res.message()}"))
                        warningMessage.postValue("Server Error $code, ${res.message()}")
                    }
                },
                { err ->
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

    fun loadHistoryTransaction() {
        dataManager.loadHistoryTransaction()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            onHistoryTransactionLoaded.postValue(response)
                        }

                        loadPendanaanInfo()
                    } else {
                        // not 20x
                        val code = res.code()
                        Timber.w(Throwable("Server Error $code, ${res.message()}"))
                        warningMessage.postValue(res.message())
                    }
                },
                { err ->
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

    fun loadDashboardBalance() {
        dataManager.loadDashboardBalance()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                balanceData = response[0]
                                onDashboardBalanceLoaded.postValue(
                                    response[0]/*.apply { iDPendanaanDisetujui = "0" }*/
                                )
                            }else{
                                warningMessage.postValue("empty data dashboard")
                            }
                        }

                        loadHistoryTransaction()
                    } else {
                        // not 20x
                        val code = res.code()
                        Timber.w(Throwable("Server Error $code, ${res.message()}"))
                        warningMessage.postValue(res.message())
                    }
                },
                { err ->
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

    fun loadProfile() {
        dataManager.loadUserProfile()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                if(response[0].status == "1"){
                                    onProfileLoaded.postValue(response[0])
                                }else{
                                    warningMessage.postValue(response[0].message!!)
                                }
                            }else{
                                warningMessage.postValue("empty data profile")
                            }
                        }

                        loadDashboardBalance()
                    } else {
                        // not 20x
                        val code = res.code()
                        Timber.w(Throwable("Server Error $code, ${res.message()}"))
                        warningMessage.postValue(res.message())
                    }
                },
                { err ->
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

    internal fun isLoggedIn(): Boolean = dataManager.getLoginState()

}