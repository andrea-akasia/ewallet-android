package com.mobile.ewallet.feature.credit.kur

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.model.api.credit.*
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
class KURCreditViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel() {
    internal var warningMessage = MutableLiveData<String>()

    internal var onFormJenisKelaminLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormPendidikanLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormKodePosLoaded = MutableLiveData<MutableList<KodePos>>()
    internal var onFormLokasiDatillLoaded = MutableLiveData<MutableList<LokasiDatill>>()
    internal var onFormStatusRumahLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormStatusPernikahanLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormJenisKreditLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormJangkaWaktuLoaded = MutableLiveData<MutableList<String>>()
    internal var onPrescreeningSuccess = MutableLiveData<Boolean>()

    //form data
    var creditRequestId = ""
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
    var jenisKreditParameter: JenisKreditParameter? = null
    var jangkaWaktus = mutableListOf<JangkaWaktu>()
    var selectedJangkaWaktu: JangkaWaktu? = null

    fun submitPrescreeningKUR(
        namaPelapor: String,
        nomorKK: String,
        tempatLahir: String,
        tanggalLahir: String,
        namaIbu: String,
        telpArea: String,
        telp: String,
        nomorKTP: String,
        namaKTP: String,
        alamatKTP: String,
        kotaKTP: String,
        kecamatanKTP: String,
        kelurahanKTP: String,
        alamatRumah: String,
        kotaRumah: String,
        kecamatanRumah: String,
        kelurahanRumah: String,
        tanggalMenempatiRumah: String,
        namaPasangan: String = "",
        tanggalLahirPasangan: String = "",
        nomorKTPPasangan: String = "",
        limitAwal: String,
        npwp: String,
        noRek: String
    ) {
        dataManager.submitPrescreeningKUR(
            KURPrescreeningBody(
                idRequest = creditRequestId,
                namaPelapor = namaPelapor,
                nomorKK = nomorKK,
                tempatLahir = tempatLahir,
                codeJenisKelamin = if(selectedJenisKelamin!=null) selectedJenisKelamin!!.code else "",
                tanggalLahir = tanggalLahir,
                codePendidikan = if(selectedPendidikan!=null) selectedPendidikan!!.code else "",
                namaIbu = namaIbu,
                noTelpArea = telpArea,
                noTelp = telp,
                nomorKTP = nomorKTP,
                namaKTP = namaKTP,
                alamatKTP = alamatKTP,
                kotaKTP = kotaKTP,
                kecamatanKTP = kecamatanKTP,
                kelurahanKTP = kelurahanKTP,
                codeKodePosKTP = if(selectedKodePosKTP!=null) selectedKodePosKTP!!.code else "",
                alamatRumah = alamatRumah,
                kotaRumah = kotaRumah,
                kecamatanRumah = kecamatanRumah,
                kelurahanRumah = kelurahanRumah,
                codeKodePosRumah = if(selectedKodePosRumah!=null) selectedKodePosRumah!!.code else "",
                codeDatillRumah = if(selectedLokasiDatill!=null) selectedLokasiDatill!!.code else "",
                codeStatusRumah = if(selectedStatusRumah!=null) selectedStatusRumah!!.code else "",
                tanggalMulaiMenempatiRumah = tanggalMenempatiRumah,
                codeStatusPernikahan = if(selectedStatusPernikahan!=null) selectedStatusPernikahan!!.code else "",
                namaPasangan = namaPasangan,
                tanggalLahirPasangan = tanggalLahirPasangan,
                nomorKTPPasangan = nomorKTPPasangan,
                codeJenisKredit = if(selectedJenisKredit!=null) selectedJenisKredit!!.code else "",
                limitAwal = limitAwal,
                codeJangkaWaktu = if(selectedJangkaWaktu!=null) selectedJangkaWaktu!!.code else "",
                npwp = npwp,
                noRek = noRek
            )
        )
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                if(response[0].message.lowercase() == "success"){
                                    onPrescreeningSuccess.postValue(true)
                                }else{
                                    warningMessage.postValue(response[0].message)
                                }
                            }else{
                                warningMessage.postValue("empty data response")
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

    fun generateCreditRequest() {
        dataManager.generateCreditRequest()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            creditRequestId = response[0].iDRequestPendanaan
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

    fun loadJangkaWaktu(jenisKredit: String) {
        selectedJangkaWaktu = null
        jangkaWaktus.clear()
        dataManager.formJangkaWaktu(jenisKredit)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            val dataString = mutableListOf<String>()
                            response.forEach {
                                jangkaWaktus.add(it)
                                if(it.description != "SELECT"){
                                    dataString.add("${it.description} Bulan")
                                }else{
                                    dataString.add(it.description)
                                }
                            }
                            onFormJangkaWaktuLoaded.postValue(dataString)
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

    fun loadJenisKreditParameter(jenisKredit: String) {
        jenisKreditParameter = null
        dataManager.loadJenisKreditParameter(jenisKredit)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            jenisKreditParameter = response[0]
                        }
                        loadJangkaWaktu(jenisKredit)
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

    fun loadJenisKredit() {
        jenisKredits.clear()
        selectedJenisKredit = null
        dataManager.formJenisKreditKUR()
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

    fun loadLokasiDatill(keyword: String) {
        lokasiDatills.clear()
        dataManager.formLokasiDatill(keyword)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            onFormLokasiDatillLoaded.postValue(response)

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
}