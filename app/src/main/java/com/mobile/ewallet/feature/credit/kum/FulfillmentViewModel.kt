package com.mobile.ewallet.feature.credit.kum

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.model.api.credit.JenisKelamin
import com.mobile.ewallet.model.api.credit.KUMFulfillmentBody
import com.mobile.ewallet.model.api.credit.Kewarganegaraan
import com.mobile.ewallet.model.api.credit.Profesi
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
class FulfillmentViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel() {
    internal var warningMessage = MutableLiveData<String>()

    internal var onFormKewarganegaraanLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormProfesiLoaded = MutableLiveData<MutableList<String>>()

    var KUMFulfillmentData = KUMFulfillmentBody()
    var creditRequestId = ""
    var kewarganegaraans = mutableListOf<Kewarganegaraan>()
    var selectedKewarganegaraan: Kewarganegaraan? = null
    var profesis = mutableListOf<Profesi>()
    var selectedProfesi: Profesi? = null

    fun loadFormProfesi() {
        selectedProfesi = null
        profesis.clear()
        dataManager.formProfesi()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            val dataString = mutableListOf<String>()
                            response.forEach {
                                profesis.add(it)
                                dataString.add(it.description)
                            }
                            onFormProfesiLoaded.postValue(dataString)
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

    fun loadFormKewarganegaraan() {
        selectedKewarganegaraan = null
        kewarganegaraans.clear()
        dataManager.formKewarganegaraan()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            val dataString = mutableListOf<String>()
                            response.forEach {
                                kewarganegaraans.add(it)
                                dataString.add(it.description)
                            }
                            onFormKewarganegaraanLoaded.postValue(dataString)
                            loadFormProfesi()
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