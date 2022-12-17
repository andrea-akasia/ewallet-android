package com.mobile.ewallet.feature.credit.kur

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.util.createMultipartFromImageFile
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@SuppressLint("CheckResult")
class KURDocumentViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel() {
    internal var warningMessage = MutableLiveData<String>()

    internal  var onKTPSuccess = MutableLiveData<Boolean>()
    internal  var onKKSuccess = MutableLiveData<Boolean>()
    internal  var onNPWPSuccess = MutableLiveData<Boolean>()
    internal  var onSelfieSuccess = MutableLiveData<Boolean>()
    internal  var onSuratSuccess = MutableLiveData<Boolean>()
    internal  var onSIUPSuccess = MutableLiveData<Boolean>()
    internal  var onTermsLoaded = MutableLiveData<String>()
    internal  var onSubmitSuccess = MutableLiveData<Boolean>()

    var creditRequestId = ""
    var TAG = ""

    fun submitFinal() {
        dataManager.submitFinalCredit(creditRequestId)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                if(response[0].status == "1"){
                                    warningMessage.postValue(response[0].message!!)
                                    onSubmitSuccess.postValue(true)
                                }else{
                                    warningMessage.postValue(response[0].message!!)
                                }
                            }else{
                                warningMessage.postValue("error empty data response")
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

    fun loadTerms() {
        dataManager.creditTerms(creditRequestId)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            onTermsLoaded.postValue(response[0].terms)
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

    fun uploadSIUP(file: File) {
        dataManager.kurDocumentSIUP(creditRequestId, createMultipartFromImageFile(file, "SIUP"))
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response[0].status == "1"){
                                onSIUPSuccess.postValue(true)
                            }else{
                                warningMessage.postValue(response[0].message!!)
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

    fun uploadSurat(file: File) {
        dataManager.kurDocumentSurat(creditRequestId, createMultipartFromImageFile(file, "SuratPengajuan"))
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response[0].status == "1"){
                                onSuratSuccess.postValue(true)
                            }else{
                                warningMessage.postValue(response[0].message!!)
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

    fun uploadSelfie(file: File) {
        dataManager.kurDocumentSelfie(creditRequestId, createMultipartFromImageFile(file, "PHOTOSELFIE"))
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response[0].status == "1"){
                                onSelfieSuccess.postValue(true)
                            }else{
                                warningMessage.postValue(response[0].message!!)
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

    fun uploadNPWP(file: File) {
        dataManager.kurDocumentNPWP(creditRequestId, createMultipartFromImageFile(file, "PHOTONPWP"))
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response[0].status == "1"){
                                onNPWPSuccess.postValue(true)
                            }else{
                                warningMessage.postValue(response[0].message!!)
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

    fun uploadKK(file: File) {
        dataManager.kurDocumentKK(creditRequestId, createMultipartFromImageFile(file, "PHOTOKK"))
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response[0].status == "1"){
                                onKKSuccess.postValue(true)
                            }else{
                                warningMessage.postValue(response[0].message!!)
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

    fun uploadKTP(file: File) {
        dataManager.kurDocumentKTP(creditRequestId, createMultipartFromImageFile(file, "PHOTOKTP"))
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response[0].status == "1"){
                                onKTPSuccess.postValue(true)
                            }else{
                                warningMessage.postValue(response[0].message!!)
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