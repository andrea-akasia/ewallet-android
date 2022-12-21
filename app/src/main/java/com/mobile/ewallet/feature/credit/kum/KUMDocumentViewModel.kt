package com.mobile.ewallet.feature.credit.kum

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.model.api.credit.preview.KUMPreviewResponse
import com.mobile.ewallet.util.createMultipartFromImageFile
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@SuppressLint("CheckResult")
class KUMDocumentViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel() {
    internal var warningMessage = MutableLiveData<String>()
    internal var isLoading = MutableLiveData<Boolean>()

    internal  var onKTPSuccess = MutableLiveData<Boolean>()
    internal  var onKKSuccess = MutableLiveData<Boolean>()
    internal  var onNPWPSuccess = MutableLiveData<Boolean>()
    internal  var onSelfieSuccess = MutableLiveData<Boolean>()
    internal  var onSuratSuccess = MutableLiveData<Boolean>()
    internal  var onTermsLoaded = MutableLiveData<String>()
    internal  var onSubmitSuccess = MutableLiveData<Boolean>()
    internal var onKUMPreviewLoaded = MutableLiveData<KUMPreviewResponse>()

    var previewKUMData: KUMPreviewResponse? = null
    var creditRequestId = ""
    var TAG = ""

    fun loadKUMPreview(idRequest: String) {
        isLoading.postValue(true)
        dataManager.previewKUM(idRequest)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    isLoading.postValue(false)
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                previewKUMData = response[0]
                                onKUMPreviewLoaded.postValue(response[0])
                            }
                        }
                    } else {
                        // not 20x
                        val code = res.code()
                        Timber.w(Throwable("Server Error $code, ${res.message()}"))
                        warningMessage.postValue("request failed, Server Error $code")
                    }
                },
                { err ->
                    isLoading.postValue(false)
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

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

    fun uploadSurat(file: File) {
        isLoading.postValue(true)
        dataManager.kumDocumentSurat(creditRequestId, createMultipartFromImageFile(file, "SuratPengajuan"))
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    isLoading.postValue(false)
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response[0].status == "1"){
                                loadKUMPreview(creditRequestId)
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
                    isLoading.postValue(false)
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

    fun uploadSelfie(file: File) {
        isLoading.postValue(true)
        dataManager.kumDocumentSelfie(creditRequestId, createMultipartFromImageFile(file, "PHOTOSELFIE"))
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    isLoading.postValue(false)
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response[0].status == "1"){
                                loadKUMPreview(creditRequestId)
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
                    isLoading.postValue(false)
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

    fun uploadNPWP(file: File) {
        isLoading.postValue(true)
        dataManager.kumDocumentNPWP(creditRequestId, createMultipartFromImageFile(file, "PHOTONPWP"))
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    isLoading.postValue(false)
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response[0].status == "1"){
                                loadKUMPreview(creditRequestId)
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
                    isLoading.postValue(false)
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

    fun uploadKK(file: File) {
        isLoading.postValue(true)
        dataManager.kumDocumentKK(creditRequestId, createMultipartFromImageFile(file, "PHOTOKK"))
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    isLoading.postValue(false)
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response[0].status == "1"){
                                loadKUMPreview(creditRequestId)
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
                    isLoading.postValue(false)
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

    fun uploadKTP(file: File) {
        isLoading.postValue(true)
        dataManager.kumDocumentKTP(creditRequestId, createMultipartFromImageFile(file, "PHOTOKTP"))
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    isLoading.postValue(false)
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response[0].status == "1"){
                                loadKUMPreview(creditRequestId)
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
                    isLoading.postValue(false)
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }
}