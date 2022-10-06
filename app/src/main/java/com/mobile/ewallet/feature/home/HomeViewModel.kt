package com.mobile.ewallet.feature.home

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.model.api.badge.Badge
import com.mobile.ewallet.model.api.badge.BadgeStatus
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

    var balanceData: DashboardBalance? = null

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

    fun loadHistoryTransaction() {
        dataManager.loadHistoryTransaction()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            onHistoryTransactionLoaded.postValue(response)
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

    fun loadDashboardBalance() {
        dataManager.loadDashboardBalance()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                balanceData = response[0]
                                onDashboardBalanceLoaded.postValue(response[0])
                            }else{
                                warningMessage.postValue("empty data dashboard")
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