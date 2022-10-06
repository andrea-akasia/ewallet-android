package com.mobile.ewallet.feature.moneysend

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.model.api.AccountItem
import com.mobile.ewallet.model.api.dashboard.DashboardBalance
import com.mobile.ewallet.model.api.dashboard.TransactionItem
import com.mobile.ewallet.model.api.sendmoney.HistoryTransferTransaction
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
class SendMoneyViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel(){

    internal var warningMessage = MutableLiveData<String>()
    internal var onHistoryTransactionLoaded = MutableLiveData<MutableList<HistoryTransferTransaction>>()

    fun loadHistoryTransfer(){
        dataManager.loadHistoryTransferTransaction()
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

    fun getDummyBank(): MutableList<String>{
        val result = mutableListOf<String>()
        result.add("BCA")
        result.add("BNI")
        result.add("Mandiri")
        result.add("BRI")
        result.add("BTPN")
        return result
    }

}