package com.mobile.ewallet.feature.credit

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.model.api.credit.JenisKelamin
import com.mobile.ewallet.model.api.dashboard.TransactionItem
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
class CreditViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel(){
    internal var warningMessage = MutableLiveData<String>()
    internal var onCreditHistoryTransactionLoaded = MutableLiveData<MutableList<TransactionItem>>()

    internal var onFormJenisKelaminLoaded = MutableLiveData<MutableList<String>>()

    //form data
    var jenisKelamins = mutableListOf<JenisKelamin>()
    var selectedJenisKelamin: JenisKelamin? = null

    fun loadFormJenisKelamin() {
        selectedJenisKelamin = null
        jenisKelamins.clear()
        dataManager.formJenisKelamin()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            val dataString = mutableListOf<String>()
                            response.forEach {
                                jenisKelamins.add(it)
                                dataString.add(it.description)
                            }
                            onFormJenisKelaminLoaded.postValue(dataString)
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

    fun loadCreditHistoryTransaction() {
        dataManager.loadCreditHistoryTransaction()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            onCreditHistoryTransactionLoaded.postValue(response)
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
}