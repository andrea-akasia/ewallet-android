package com.mobile.ewallet.feature.pay

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.model.api.sendmoney.byscan.AdminFeeResponse
import com.mobile.ewallet.model.api.sendmoney.byscan.MinimumNominalResponse
import com.mobile.ewallet.model.api.sendmoney.byscan.SendMoneyResult
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
class PayViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel(){

    internal var warningMessage = MutableLiveData<String>()
    internal var isLoading = MutableLiveData<Boolean>()
    internal var onMinimumNominalLoaded = MutableLiveData<MinimumNominalResponse>()
    internal var onAdminFeeLoaded = MutableLiveData<AdminFeeResponse>()
    internal var onTransactionSuccess = MutableLiveData<SendMoneyResult>()

    var action = "QR" //QR,BANK
    var minimumAmount = 0
    var adminFee = 0
    var total = 0

    fun scanSendMoney(qr: String, amount: String, adminFee: String, total: String) {
        isLoading.postValue(true)
        dataManager.scanSendMoney(qr, amount, adminFee, total)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    isLoading.postValue(false)
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                onTransactionSuccess.postValue(response[0])
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
                    isLoading.postValue(false)
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

    fun loadAdminFee(qr: String, amount: String) {
        isLoading.postValue(true)
        dataManager.scanLoadAdminFee(qr, amount)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    isLoading.postValue(false)
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                adminFee = response[0].adminFee.toInt()
                                total = response[0].total.toInt()
                                onAdminFeeLoaded.postValue(response[0])
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
                    isLoading.postValue(false)
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

    fun loadMinimumNominal(qr: String) {
        isLoading.postValue(true)
        dataManager.scanLoadMinimumNominal(qr)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    isLoading.postValue(false)
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                minimumAmount = response[0].minimal.toInt()
                                onMinimumNominalLoaded.postValue(response[0])
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
                    isLoading.postValue(false)
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

}