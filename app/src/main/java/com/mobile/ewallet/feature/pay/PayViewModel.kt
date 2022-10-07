package com.mobile.ewallet.feature.pay

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.model.api.sendmoney.banktransfer.AdminFeeTrfResponse
import com.mobile.ewallet.model.api.sendmoney.banktransfer.MinimumNominalTrfResponse
import com.mobile.ewallet.model.api.sendmoney.bycontact.ContactUser
import com.mobile.ewallet.model.api.sendmoney.byscan.AdminFeeResponse
import com.mobile.ewallet.model.api.sendmoney.byscan.MinimumNominalResponse
import com.mobile.ewallet.model.api.sendmoney.byscan.SendMoneyResult
import com.mobile.ewallet.util.formatToCurrency
import com.mobile.ewallet.util.removeCharExceptNumber
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

    internal var onAdminFeeTransferLoaded = MutableLiveData<AdminFeeTrfResponse>()
    lateinit var transferBankMinimumNominalData: MinimumNominalTrfResponse

    lateinit var contactUser: ContactUser

    var action = "QR" //QR,BANK,CONTACT
    var minimumAmount = 0
    var adminFee = 0
    var total = 0

    fun loadContactAdminFee(idMemberDesetination: String, amount: String) {
        isLoading.postValue(true)
        dataManager.contactLoadAdminFee(idMemberDesetination, amount)
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

    fun loadContactMinimumNominal(idMemberDestination: String) {
        isLoading.postValue(true)
        dataManager.contactLoadMinimumNominal(idMemberDestination)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    isLoading.postValue(false)
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                minimumAmount = response[0].minimal.toInt()
                                onMinimumNominalLoaded.postValue(
                                    MinimumNominalResponse(
                                        minimal = response[0].minimal,
                                        minimalText = response[0].minimal.formatToCurrency()
                                    ).apply {
                                        destinationPhoto = response[0].destinationPhoto
                                        destinationName = response[0].destinationName
                                        destinationPhone = response[0].destinationPhone
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
                    isLoading.postValue(false)
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

    fun transferSendMoney(
        idBank: String,
        accountNumber: String,
        name: String,
        amount: String,
        adminFee: String,
        total: String
    ) {
        isLoading.postValue(true)
        dataManager.transferSendMoney(idBank, accountNumber, name, amount, adminFee, total)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    isLoading.postValue(false)
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                onTransactionSuccess.postValue(
                                    SendMoneyResult(
                                        idTransaction = response[0].iDTransaksi!!,
                                        transactionType = response[0].type!!,
                                        metodeBayar = response[0].metodeBayar!!,
                                        reffNumber = response[0].reff!!,
                                        time = response[0].time!!,
                                        amount = response[0].amount!!,
                                        adminFeeText = response[0].adminFee!!,
                                        total = response[0].total!!
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
                    isLoading.postValue(false)
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

    fun loadAdminFeeTransfer(idBank: String, accountNumber: String, amount: String) {
        isLoading.postValue(true)
        dataManager.transferLoadAdminFee(idBank, accountNumber, amount)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    isLoading.postValue(false)
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                Timber.i("adminfee: ${response[0].biayaAdmin.removeCharExceptNumber()}")
                                adminFee = response[0].biayaAdmin.removeCharExceptNumber().toInt()
                                total = adminFee + amount.toInt()
                                onAdminFeeTransferLoaded.postValue(response[0])
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