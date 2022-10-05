package com.mobile.ewallet.feature.moneyreq

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.model.api.moneyrequest.MoneyRequestData
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
class MoneyRequestViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel(){
    internal var onDataLoaded = MutableLiveData<MoneyRequestData>()
    internal var warningMessage = MutableLiveData<String>()

    fun loadData() {
        dataManager.loadMoneyRequest()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                onDataLoaded.postValue(response[0])
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
}