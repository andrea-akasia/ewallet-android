package com.mobile.ewallet.feature.credit.kum

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.model.api.credit.*
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
class FulfillmentViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel() {
    internal var warningMessage = MutableLiveData<String>()

    internal var onFormKewarganegaraanLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormProfesiLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormJabatanLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormBidangUsahaLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormTempatBekerjaLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormKodePosLoaded = MutableLiveData<MutableList<KodePos>>()

    var KUMFulfillmentData = KUMFulfillmentBody()
    var creditRequestId = ""
    var kewarganegaraans = mutableListOf<Kewarganegaraan>()
    var selectedKewarganegaraan: Kewarganegaraan? = null
    var profesis = mutableListOf<Profesi>()
    var selectedProfesi: Profesi? = null
    var jabatans = mutableListOf<Jabatan>()
    var selectedJabatan: Jabatan? = null
    var bidangUsahas = mutableListOf<BidangUsaha>()
    var selectedBidangUsaha: BidangUsaha? = null
    var TAG_DATE = ""
    var tempatBekerjas = mutableListOf<TempatBekerja>()
    var selectedTempatBekerja: TempatBekerja? = null
    var TAG_KODE_POS = ""
    var kodePoss = mutableListOf<KodePos>()
    var selectedKodePosKantor: KodePos? = null

    fun loadKodePos(keyword: String) {
        kodePoss.clear()
        dataManager.formKodePos(keyword)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            response.forEach {
                                kodePoss.add(it)
                            }
                            if(kodePoss.size > 1){ kodePoss.removeAt(0) }
                            onFormKodePosLoaded.postValue(kodePoss)
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

    fun loaddFormTempatBekerja() {
        selectedTempatBekerja = null
        tempatBekerjas.clear()
        dataManager.formTempatBekerja()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            val dataString = mutableListOf<String>()
                            response.forEach {
                                tempatBekerjas.add(it)
                                dataString.add(it.description)
                            }
                            onFormTempatBekerjaLoaded.postValue(dataString)
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

    fun loadFormBidangUsaha() {
        selectedBidangUsaha = null
        bidangUsahas.clear()
        dataManager.formBidangUsaha()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            val dataString = mutableListOf<String>()
                            response.forEach {
                                bidangUsahas.add(it)
                                dataString.add(it.description)
                            }
                            onFormBidangUsahaLoaded.postValue(dataString)
                            loaddFormTempatBekerja()
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

    fun loadFormJabatan() {
        selectedJabatan = null
        jabatans.clear()
        dataManager.formJabatan()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            val dataString = mutableListOf<String>()
                            response.forEach {
                                jabatans.add(it)
                                dataString.add(it.description)
                            }
                            onFormJabatanLoaded.postValue(dataString)
                            loadFormBidangUsaha()
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
                            loadFormJabatan()
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