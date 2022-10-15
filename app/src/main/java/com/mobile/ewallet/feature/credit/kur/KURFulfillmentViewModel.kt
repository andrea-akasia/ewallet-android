package com.mobile.ewallet.feature.credit.kur

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.model.api.credit.*
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
class KURFulfillmentViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel() {
    internal var warningMessage = MutableLiveData<String>()

    internal var onFormKewarganegaraanLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormProfesiLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormJabatanLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormBidangUsahaLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormTempatBekerjaLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormKodePosLoaded = MutableLiveData<MutableList<KodePos>>()
    internal var onFormStatusPernikahanLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormLokasiDatillLoaded = MutableLiveData<MutableList<LokasiDatill>>()
    internal var onFormSumberDanaLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormKomoditasLoaded = MutableLiveData<MutableList<String>>()
    internal var onFormJenisDebiturLoaded = MutableLiveData<MutableList<String>>()
    internal  var onFulfillmentSuccess = MutableLiveData<Boolean>()

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
    var statusPernikahans = mutableListOf<StatusPernikahan>()
    var selectedStatusPernikahan: StatusPernikahan? = null
    var selectedProfesiPasangan: Profesi? = null
    var selectedTempatBekerjaPasangan: TempatBekerja? = null
    var selectedBidangUsahaPasangan: BidangUsaha? = null
    var lokasiDatills = mutableListOf<LokasiDatill>()
    var selectedLokasiDatill: LokasiDatill? = null
    var sumberDanas = mutableListOf<SumberDana>()
    var selectedSumberDana: SumberDana? = null
    var komoditass = mutableListOf<Komoditas>()
    var selectedKomoditas: Komoditas? = null
    var jenisDebiturs = mutableListOf<JenisDebitur>()
    var selectedJenisDebitur: JenisDebitur? = null

    fun submitFulfillmentKUR(
        phone: String,
        faxArea: String,
        fax: String,
        berdiriSejak: String,
        bekerjaSejak: String,
        namaPerusahaan: String,
        alamatKantor1: String,
        alamatKantor2: String,
        alamatKantor3: String,
        kecamatanKantor: String,
        kelurahanKantor: String,
        faxAreaKantor: String,
        faxKantor: String,
        telpAreaKantor: String,
        telpKantor: String,
        emergencyName: String,
        tanggalMenikah: String,
        luasLahan: String,
        suratPermohonan: String,
        ijinUsaha: String
    ) {
        dataManager.submitFulfillmentKUR(
            KURFulfillmentBody(
                idRequest = creditRequestId,
                codeKewarganegaraan = if (selectedKewarganegaraan != null) selectedKewarganegaraan!!.code else "",
                nomorPonsel = phone,
                faxArea = faxArea,
                fax = fax,
                codeProfesi = if (selectedProfesi != null) selectedProfesi!!.code else "",
                codeJabatan = if (selectedJabatan != null) selectedJabatan!!.code else "",
                codeBidangUsaha = if (selectedBidangUsaha != null) selectedBidangUsaha!!.code else "",
                berdiriSejak = berdiriSejak,
                bekerjaSejak = bekerjaSejak,
                codeTempatBekerja = if (selectedTempatBekerja != null) selectedTempatBekerja!!.code else "",
                namaPerusahaan = namaPerusahaan,
                alamatKantor1 = alamatKantor1,
                alamatKantor2 = alamatKantor2,
                alamatKantor3 = alamatKantor3,
                kecamatanKantor = kecamatanKantor,
                kelurahanKantor = kelurahanKantor,
                codeKodePosKantor = if (selectedKodePosKantor != null) selectedKodePosKantor!!.code else "",
                faxAreaKantor = faxAreaKantor,
                faxKantor = faxKantor,
                telpAreaKantor = telpAreaKantor,
                telpKantor = telpKantor,
                kontakDarurat = emergencyName,
                codeHubungan = if (selectedStatusPernikahan != null) selectedStatusPernikahan!!.code else "",
                codeProfesiPasangan = if (selectedProfesiPasangan != null) selectedProfesiPasangan!!.code else "",
                codeTempatBekerjaPasangan = if (selectedTempatBekerjaPasangan != null) selectedTempatBekerjaPasangan!!.code else "",
                codeBidangUsahaPasangan = if (selectedBidangUsahaPasangan != null) selectedBidangUsahaPasangan!!.code else "",
                tanggalMenikah = tanggalMenikah,
                codeDatill = if (selectedLokasiDatill != null) selectedLokasiDatill!!.code else "",
                codeSumberDana = if (selectedSumberDana != null) selectedSumberDana!!.code else "",
                codeKomoditas = if (selectedKomoditas != null) selectedKomoditas!!.code else "",
                luasLahan = luasLahan,
                codeJenisDebitur = if (selectedJenisDebitur != null) selectedJenisDebitur!!.code else "",
                suratPermohonan = suratPermohonan,
                ijinUsaha = ijinUsaha
            )
        )
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if (response.isNotEmpty()) {
                                if (response[0].message.lowercase() == "success") {
                                    onFulfillmentSuccess.postValue(true)
                                } else {
                                    warningMessage.postValue(response[0].message)
                                }
                            } else {
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

    fun loadJenisDebitur() {
        jenisDebiturs.clear()
        selectedJenisDebitur = null
        dataManager.formJenisDebitur()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            val dataString = mutableListOf<String>()
                            response.forEach {
                                jenisDebiturs.add(it)
                                dataString.add(it.description)
                            }
                            onFormJenisDebiturLoaded.postValue(dataString)

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

    fun loadKomoditas() {
        komoditass.clear()
        selectedKomoditas = null
        dataManager.formKomoditas()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            val dataString = mutableListOf<String>()
                            response.forEach {
                                komoditass.add(it)
                                dataString.add(it.description)
                            }
                            onFormKomoditasLoaded.postValue(dataString)
                            loadJenisDebitur()
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

    fun loadSumberDana() {
        sumberDanas.clear()
        selectedSumberDana = null
        dataManager.formSumberDana()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            val dataString = mutableListOf<String>()
                            response.forEach {
                                sumberDanas.add(it)
                                dataString.add(it.description)
                            }
                            onFormSumberDanaLoaded.postValue(dataString)
                            loadKomoditas()
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
                            loadSumberDana()
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