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
    internal var onReqOTPRegisterSuccess = MutableLiveData<String>()
    internal var onResendOTPRegisterSuccess = MutableLiveData<String>()
    internal var onConfirmOTPSuccess = MutableLiveData<Boolean>()
    internal var onRegisterFinished = MutableLiveData<Boolean>()

    fun finishRegister(phone: String, fullName: String) {
        isLoading.postValue(true)
        dataManager.finishRegister(phone, fullName)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    isLoading.postValue(false)
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                if(response[0].status == "1"){
                                    dataManager.setLoginState(true)
                                    onRegisterFinished.postValue(true)
                                }else{
                                    warningMessage.postValue(response[0].message!!)
                                }
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

    fun confirmOTPRegister(phone: String, uuid: String, otp: String) {
        isLoading.postValue(true)
        dataManager.confirmOTPRegister(phone, uuid, otp)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    isLoading.postValue(false)
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                if(response[0].message!!.lowercase() == "success"){
                                    Timber.i("idmember: ${response[0].iDMember}")
                                    dataManager.setIdMember(response[0].iDMember!!)
                                    onConfirmOTPSuccess.postValue(true)
                                }else{
                                    warningMessage.postValue(response[0].message!!)
                                }
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

    fun resendOTPRegister(phone: String, uuid: String) {
        isLoading.postValue(true)
        dataManager.requestResendOTPRegister(phone, uuid)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    isLoading.postValue(false)
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                onResendOTPRegisterSuccess.postValue(response[0].message!!)
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

    fun requestOTPRegister(phone: String, uuid: String) {
        isLoading.postValue(true)
        dataManager.requestOTPRegister(phone, uuid)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    isLoading.postValue(false)
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                onReqOTPRegisterSuccess.postValue(response[0].message!!)
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