package com.mobile.ewallet.feature.topup

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.model.api.topup.TopupVA
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
class TopupViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel() {

    internal var warningMessage = MutableLiveData<String>()
    internal var isLoading = MutableLiveData<Boolean>()
    internal var onVirtualAccountLoaded = MutableLiveData<MutableList<TopupVA>>()

    fun loadVirtualAcc() {
        isLoading.postValue(true)
        dataManager.loadListVirtualAcc()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    isLoading.postValue(false)
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            onVirtualAccountLoaded.postValue(response)
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