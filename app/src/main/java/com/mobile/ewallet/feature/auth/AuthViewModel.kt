package com.mobile.ewallet.feature.auth

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.model.api.splashscreen.SplashscreenAPIResponse
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
class AuthViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel(){

    internal var isLoading = MutableLiveData<Boolean>()
    internal var warningMessage = MutableLiveData<String>()
    internal var onSplashscreenLoaded = MutableLiveData<SplashscreenAPIResponse>()

    fun loadSplashScreen() {
        isLoading.postValue(true)
        dataManager.splashscreen()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    isLoading.postValue(false)
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                onSplashscreenLoaded.postValue(response[0])
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