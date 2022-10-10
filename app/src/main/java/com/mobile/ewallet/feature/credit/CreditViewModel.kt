package com.mobile.ewallet.feature.credit

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.model.api.credit.*
import com.mobile.ewallet.model.api.dashboard.TransactionItem
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
class CreditViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel(){
    internal var warningMessage = MutableLiveData<String>()
    internal var onCreditHistoryTransactionLoaded = MutableLiveData<MutableList<TransactionItem>>()

    internal var onFormJenisKelaminLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormPendidikanLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormKodePosLoaded = MutableLiveData<MutableList<KodePos>>()
    internal var onFormLokasiDatillLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormStatusRumahLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormStatusPernikahanLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormJenisKreditLoaded = MutableLiveData<MutableList<String>>()

    //form data
    var prescreeningKUMData = KUMPrescreeningBody()
    var jenisKelamins = mutableListOf<JenisKelamin>()
    var selectedJenisKelamin: JenisKelamin? = null
    var TAG_DATE = "" //BIRTHDATE
    var pendidikans = mutableListOf<Pendidikan>()
    var selectedPendidikan: Pendidikan? = null
    var TAG_KODE_POS = "" //KTP
    var kodePoss = mutableListOf<KodePos>()
    var selectedKodePosKTP: KodePos? = null
    var selectedKodePosRumah: KodePos? = null
    var lokasiDatills = mutableListOf<LokasiDatill>()
    var selectedLokasiDatill: LokasiDatill? = null
    var statusRumahs = mutableListOf<StatusRumah>()
    var selectedStatusRumah: StatusRumah? = null
    var statusPernikahans = mutableListOf<StatusPernikahan>()
    var selectedStatusPernikahan: StatusPernikahan? = null
    var jenisKredits = mutableListOf<JenisKredit>()
    var selectedJenisKredit: JenisKredit? = null

    fun loadJenisKredit() {
        jenisKredits.clear()
        selectedJenisKredit = null
        dataManager.formJenisKreditKUM()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            val dataString = mutableListOf<String>()
                            response.forEach {
                                jenisKredits.add(it)
                                dataString.add(it.description)
                            }
                            onFormJenisKreditLoaded.postValue(dataString)
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

    fun loadStatusPernikahan() {
        statusPernikahans.clear()
        selectedStatusPernikahan = null
        dataManager.formStatusPernikahan()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            val dataString = mutableListOf<String>()
                            response.forEach {
                                statusPernikahans.add(it)
                                dataString.add(it.description)
                            }
                            onFormStatusPernikahanLoaded.postValue(dataString)
                            loadJenisKredit()
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

    fun loadStatusRumah() {
        statusRumahs.clear()
        selectedStatusRumah = null
        dataManager.formStatusRumah()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            val dataString = mutableListOf<String>()
                            response.forEach {
                                statusRumahs.add(it)
                                dataString.add(it.description)
                            }
                            onFormStatusRumahLoaded.postValue(dataString)
                            loadStatusPernikahan()
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

    fun loadLokasiDatill() {
        lokasiDatills.clear()
        selectedLokasiDatill = null
        dataManager.formLokasiDatill()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            val dataString = mutableListOf<String>()
                            response.forEach {
                                lokasiDatills.add(it)
                                dataString.add(it.description)
                            }
                            onFormLokasiDatillLoaded.postValue(dataString)
                            loadStatusRumah()
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

    fun loadFormPendidikan() {
        selectedPendidikan = null
        pendidikans.clear()
        dataManager.formPendidikanTerakhir()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            val dataString = mutableListOf<String>()
                            response.forEach {
                                pendidikans.add(it)
                                dataString.add(it.description)
                            }
                            onFormPendidikanLoaded.postValue(dataString)
                            loadLokasiDatill()
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

    fun loadFormJenisKelamin() {
        selectedJenisKelamin = null
        jenisKelamins.clear()
        dataManager.formJenisKelamin()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            val dataString = mutableListOf<String>()
                            response.forEach {
                                jenisKelamins.add(it)
                                dataString.add(it.description)
                            }
                            onFormJenisKelaminLoaded.postValue(dataString)
                            loadFormPendidikan()
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

    fun loadCreditHistoryTransaction() {
        dataManager.loadCreditHistoryTransaction()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            onCreditHistoryTransactionLoaded.postValue(response)
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