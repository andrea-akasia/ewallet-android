package com.mobile.ewallet.feature.credit.detail.kum

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.model.api.credit.detailrequest.kur.DetailKUMDocument
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
class DetailKUMViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel() {

    internal var warningMessage = MutableLiveData<String>()
    internal var onDocumentLoaded = MutableLiveData<DetailKUMDocument>()

    fun loadDocument(id: String) {
        dataManager.detailKUMDocument(id)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                onDocumentLoaded.postValue(response[0])
                            }else{
                                warningMessage.postValue("empty data response")
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
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

}