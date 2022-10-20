package com.mobile.ewallet.feature.credit.billing

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.model.api.credit.billing.BillingCredit
import com.mobile.ewallet.model.api.credit.billing.BillingTransaction
import com.mobile.ewallet.model.api.credit.billing.BillingVA
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
class BillingViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel() {

    internal var warningMessage = MutableLiveData<String>()
    internal var isLoading = MutableLiveData<Boolean>()
    internal var onBillingDetailLoaded = MutableLiveData<MutableList<BillingVA>>()
    internal var onBillingHistoryLoaded = MutableLiveData<MutableList<BillingTransaction>>()

    lateinit var billingData: BillingCredit

    fun loadBillingHistory() {
        isLoading.postValue(true)
        dataManager.billingHistory()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    isLoading.postValue(false)
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            onBillingHistoryLoaded.postValue(response)
                        }
                    } else {
                        // not 20x
                        val code = res.code()
                        Timber.w(Throwable("Server Error $code, ${res.message()}"))
                        warningMessage.postValue("Server Error $code, ${res.message()}")
                    }
                },
                { err ->
                    isLoading.postValue(false)
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

    fun loadBillingDetail(idBilling: String) {
        isLoading.postValue(true)
        dataManager.billingDetail(idBilling)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    isLoading.postValue(false)
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            onBillingDetailLoaded.postValue(response)
                        }
                    } else {
                        // not 20x
                        val code = res.code()
                        Timber.w(Throwable("Server Error $code, ${res.message()}"))
                        warningMessage.postValue("Server Error $code, ${res.message()}")
                    }
                },
                { err ->
                    isLoading.postValue(false)
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }
}