package com.mobile.ewallet.feature.credit.kum

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.util.createMultipartFromImageFile
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@SuppressLint("CheckResult")
class KUMDocumentViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel() {
    internal var warningMessage = MutableLiveData<String>()

    internal  var onKTPSuccess = MutableLiveData<Boolean>()

    var creditRequestId = ""
    var TAG = ""

    fun uploadKTP(file: File) {
        dataManager.kumDocumentKTP(creditRequestId, createMultipartFromImageFile(file, "PHOTOKTP"))
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