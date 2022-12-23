package com.mobile.ewallet.feature.credit.kur

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityKurPrescreeningBinding
import com.mobile.ewallet.feature.credit.DatillSearchDialog
import com.mobile.ewallet.feature.credit.KodePosSearchDialog
import com.mobile.ewallet.model.api.credit.KodePos
import com.mobile.ewallet.model.api.credit.LokasiDatill
import com.mobile.ewallet.util.DatePickerFragment
import com.mobile.ewallet.util.LoadingDialog
import com.mobile.ewallet.util.getMaxDateForBirthDate

class KURPrecreeningActivity: BaseActivity<KURCreditViewModel>(), DatePickerFragment.DateListener,
    KodePosSearchDialog.SearchKodePosListener, DatillSearchDialog.SearchDatillListener {

    override val viewModelClass: Class<KURCreditViewModel> get() = KURCreditViewModel::class.java
    private lateinit var binding: ActivityKurPrescreeningBinding

    private var searchKodePosDialog: KodePosSearchDialog? = null
    private var searchDatillDialog: DatillSearchDialog? = null
    private var loadingView: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKurPrescreeningBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.topbar.title.text = "KUR Pre Screening"

        observeViewModel()
        viewModel.generateCreditRequest()
        viewModel.loadFormJenisKelamin()

        binding.etTanggalLahir.setOnClickListener {
            viewModel.TAG_DATE = "BIRTHDATE"
            val datePicker = DatePickerFragment(maxDate = getMaxDateForBirthDate())
            datePicker.isCancelable = true
            datePicker.listener = this@KURPrecreeningActivity
            datePicker.show(supportFragmentManager, null)
        }

        binding.etKtpKodepos.setOnClickListener {
            viewModel.TAG_KODE_POS = "KTP"
            searchKodePosDialog = KodePosSearchDialog().newInstance()
            searchKodePosDialog?.listener = this@KURPrecreeningActivity
            searchKodePosDialog?.show(supportFragmentManager, null)
        }

        binding.etHomeKodepos.setOnClickListener {
            viewModel.TAG_KODE_POS = "HOME"
            searchKodePosDialog = KodePosSearchDialog().newInstance()
            searchKodePosDialog?.listener = this@KURPrecreeningActivity
            searchKodePosDialog?.show(supportFragmentManager, null)
        }

        binding.etDatill.setOnClickListener {
            searchDatillDialog = DatillSearchDialog().newInstance()
            searchDatillDialog?.listener = this@KURPrecreeningActivity
            searchDatillDialog?.show(supportFragmentManager, null)
        }

        binding.etHomeTanggalMenempati.setOnClickListener {
            viewModel.TAG_DATE = "HOME"
            val datePicker = DatePickerFragment()
            datePicker.isCancelable = true
            datePicker.listener = this@KURPrecreeningActivity
            datePicker.show(supportFragmentManager, null)
        }

        binding.etTanggalLahirPasangan.setOnClickListener {
            viewModel.TAG_DATE = "SPOUSE"
            val datePicker = DatePickerFragment(maxDate = getMaxDateForBirthDate())
            datePicker.isCancelable = true
            datePicker.listener = this@KURPrecreeningActivity
            datePicker.show(supportFragmentManager, null)
        }

        binding.btnContinue.setOnClickListener {
            validateForm()
        }
    }

    private fun validateForm(){
        if(viewModel.jenisKreditParameter != null){
            viewModel.jenisKreditParameter?.let { jenisKreditParameter ->
                if(binding.etLimit.text.toString().isNotEmpty()){
                    val limitInputed = binding.etLimit.text.toString().toInt()
                    if(limitInputed < jenisKreditParameter.minLimit.toInt()){
                        Toast.makeText(
                            this,
                            "Limit minimal adalah ${jenisKreditParameter.minLimit}",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }
                    if(limitInputed > jenisKreditParameter.maxLimit.toInt()){
                        Toast.makeText(
                            this,
                            "Limit maksimal adalah ${jenisKreditParameter.maxLimit}",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }

                    if(binding.etNomorKk.text.toString().length != 16){
                        Toast.makeText(
                            this,
                            "panjang inputan Nomor KK harus berjumlah 16 digit",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }
                    if(binding.etNomorKtp.text.toString().length != 16){
                        Toast.makeText(
                            this,
                            "panjang inputan Nomor KTP harus berjumlah 16 digit",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }
                    viewModel.selectedStatusPernikahan?.let {
                        if(viewModel.selectedStatusPernikahan!!.code == "1"){
                            if(binding.etKtpPasangan.text.toString().length != 16){
                                Toast.makeText(
                                    this,
                                    "panjang inputan Nomor KTP Pasangan harus berjumlah 16 digit",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return
                            }
                        }
                    }

                    if(binding.etNpwp.text.toString().length != 16){
                        Toast.makeText(
                            this,
                            "panjang inputan Nomor NPWP harus berjumlah 16 digit",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }

                    viewModel.submitPrescreeningKUR(
                        namaPelapor = binding.etPelaporanName.text.toString(),
                        nomorKK = binding.etNomorKk.text.toString(),
                        tempatLahir = binding.etTempatLahir.text.toString(),
                        tanggalLahir = binding.etTanggalLahir.text.toString(),
                        namaIbu = binding.etMotherName.text.toString(),
                        telpArea = binding.etPhoneArea.text.toString(),
                        telp = binding.etPhone.text.toString(),
                        nomorKTP = binding.etNomorKtp.text.toString(),
                        namaKTP = binding.etKtpName.text.toString(),
                        alamatKTP = binding.etKtpAddress.text.toString(),
                        kotaKTP = binding.etKtpKota.text.toString(),
                        kecamatanKTP = binding.etKtpKecamatan.text.toString(),
                        kelurahanKTP = binding.etKtpKelurahan.text.toString(),
                        alamatRumah = binding.etHomeAddress.text.toString(),
                        kotaRumah = binding.etHomeKota.text.toString(),
                        kecamatanRumah = binding.etHomeKecamatan.text.toString(),
                        kelurahanRumah = binding.etHomeKelurahan.text.toString(),
                        tanggalMenempatiRumah = binding.etHomeTanggalMenempati.text.toString(),
                        namaPasangan = binding.etNamaPasangan.text.toString(),
                        tanggalLahirPasangan = binding.etTanggalLahirPasangan.text.toString(),
                        nomorKTPPasangan = binding.etKtpPasangan.text.toString(),
                        limitAwal = binding.etLimit.text.toString(),
                        npwp = binding.etNpwp.text.toString(),
                        noRek = binding.etNorek.text.toString()
                    )
                }else{
                    Toast.makeText(
                        this@KURPrecreeningActivity,
                        "Limit awal harus diisi",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }else{
            Toast.makeText(this, "Jenis kredit harus dipilih!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel(){
        viewModel.onKURPreviewLoaded.observe(this){ data ->
            binding.etPelaporanName.setText(data.namaPelaporan)
            binding.etNomorKk.setText(data.nomorKartuKeluarga)
            binding.etTempatLahir.setText(data.tempatLahir)
            data.jenisKelamin?.let {
                binding.spinnerJenisKelamin.setSelection(
                    viewModel.jenisKelamins.indexOfFirst { it.code == data.jenisKelamin }
                )
            }
            binding.etTanggalLahir.setText(data.tanggalLahir)
            data.pendidikanTerakhir?.let {
                binding.spinnerPendidikan.setSelection(
                    viewModel.pendidikans.indexOfFirst { it.code == data.pendidikanTerakhir }
                )
            }
            binding.etMotherName.setText(data.namaGadisIbuKandung)
            binding.etPhoneArea.setText(data.telpygdptdihubungiArea)
            binding.etPhone.setText(data.telpygdptdihubungi)

            binding.etNomorKtp.setText(data.nomorKTP)
            binding.etKtpName.setText(data.namaSesuaiKTP)
            binding.etKtpAddress.setText(data.alamat)
            binding.etKtpKota.setText(data.kotaKTP)
            binding.etKtpKelurahan.setText(data.kelurahanKTP)
            binding.etKtpKecamatan.setText(data.kecamatanKTP)
            binding.etKtpKodepos.setText(data.kodePosKTPTEXT)
            data.kodePosKTP?.let { viewModel.selectedKodePosKTP = KodePos().apply { code = it } }

            binding.etHomeAddress.setText(data.alamatRumah)
            binding.etHomeKota.setText(data.kotaAlamatRumah)
            binding.etHomeKecamatan.setText(data.kecamatanRumah)
            binding.etHomeKelurahan.setText(data.kelurahanRumah)
            binding.etHomeKodepos.setText(data.kodePosRumahTEXT)
            data.kodePosRumah?.let { viewModel.selectedKodePosRumah = KodePos().apply { code = it } }
            binding.etDatill.setText(data.lokasiDatiIIRumahTEXT)
            data.lokasiDatiIIRumah?.let { viewModel.selectedLokasiDatill = LokasiDatill().apply { code = it } }
            binding.etHomeTanggalMenempati.setText(data.mulaiMenempati)
            data.statusRumah?.let {
                binding.spinnerStatusRumah.setSelection(
                    viewModel.statusRumahs.indexOfFirst { it.code == data.statusRumah }
                )
            }

            data.statusPernikahan?.let {
                if(it.isNotEmpty()){
                    binding.spinnerPernikahan.setSelection(
                        viewModel.statusPernikahans.indexOfFirst { sp -> sp.code == it }
                    )
                }
            }
            binding.etKtpPasangan.setText(data.nomorKTPPasangan)
            binding.etNamaPasangan.setText(data.namaSuamiIstri)
            binding.etTanggalLahirPasangan.setText(data.tanggalLahirPasangan)

            binding.spinnerJenisKredit.setSelection(
                viewModel.jenisKredits.indexOfFirst { jenisKredit -> jenisKredit.code == data.jenisKredit }
            )
            binding.etLimit.setText(data.limitAwalYangDiminta)
            binding.etNpwp.setText(data.nPWP)
            binding.etNorek.setText(data.noRekening)
        }

        viewModel.isLoading.observe(this){
            if(it){
                loadingView = LoadingDialog().newInstance()
                loadingView?.isCancelable = false
                loadingView?.show(supportFragmentManager, null)
            }else{
                loadingView?.dismiss()
            }
        }

        viewModel.onPrescreeningSuccess.observe(this){
            startActivity(
                Intent(this, KURFulfillmentActivity::class.java)
                    .putExtra("ID_REQUEST", viewModel.creditRequestId)
                    .putExtra("SELECTED_STATUS_KAWIN", viewModel.selectedStatusPernikahan?.code)
            )
        }

        viewModel.onFormJangkaWaktuLoaded.observe(this){
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it).also { adptr ->
                adptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                binding.spinnerJangkaWaktu.adapter = adptr
                binding.spinnerJangkaWaktu.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if(position > 0){
                            viewModel.selectedJangkaWaktu = viewModel.jangkaWaktus[position]
                        }else{
                            viewModel.selectedJangkaWaktu = null
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }

            viewModel.previewKURData?.let { data ->
                binding.spinnerJangkaWaktu.setSelection(
                    viewModel.jangkaWaktus.indexOfFirst { jw -> jw.code == data.jangkaWaktu }
                )
            }
        }

        viewModel.onFormJenisKreditLoaded.observe(this){
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it).also { adptr ->
                adptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                binding.spinnerJenisKredit.adapter = adptr
                binding.spinnerJenisKredit.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if(position > 0){
                            viewModel.selectedJenisKredit = viewModel.jenisKredits[position]
                            viewModel.selectedJenisKredit?.let { jenisKredit -> viewModel.loadJenisKreditParameter(jenisKredit.code) }
                        }else{
                            viewModel.selectedJenisKredit = null
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
        }

        viewModel.onFormStatusPernikahanLoaded.observe(this){
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it).also { adptr ->
                adptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                binding.spinnerPernikahan.adapter = adptr
                binding.spinnerPernikahan.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if(position > 0){
                            viewModel.selectedStatusPernikahan = viewModel.statusPernikahans[position]
                            viewModel.selectedStatusPernikahan?.let { status ->
                                if(status.code == "1"){
                                    binding.viewPernikahanOptional.visibility = View.VISIBLE
                                }else{
                                    binding.viewPernikahanOptional.visibility = View.GONE
                                }
                            }
                        }else{
                            viewModel.selectedStatusPernikahan = null
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
        }

        viewModel.onFormStatusRumahLoaded.observe(this){
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it).also { adptr ->
                adptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                binding.spinnerStatusRumah.adapter = adptr
                binding.spinnerStatusRumah.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if(position > 0){
                            viewModel.selectedStatusRumah = viewModel.statusRumahs[position]
                        }else{
                            viewModel.selectedStatusRumah = null
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
        }

        viewModel.onFormLokasiDatillLoaded.observe(this){
            searchDatillDialog?.updateData(it)
        }

        viewModel.onFormKodePosLoaded.observe(this){
            searchKodePosDialog?.updateData(it)
        }

        viewModel.onFormPendidikanLoaded.observe(this){
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it).also { adptr ->
                adptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                binding.spinnerPendidikan.adapter = adptr
                binding.spinnerPendidikan.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if(position > 0){
                            viewModel.selectedPendidikan = viewModel.pendidikans[position]
                        }else{
                            viewModel.selectedPendidikan = null
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
        }

        viewModel.onFormJenisKelaminLoaded.observe(this){
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it).also { adptr ->
                adptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                binding.spinnerJenisKelamin.adapter = adptr
                binding.spinnerJenisKelamin.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if(position > 0){
                            viewModel.selectedJenisKelamin = viewModel.jenisKelamins[position]
                        }else{
                            viewModel.selectedJenisKelamin = null
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDateSelected(date: String) {
        if(viewModel.TAG_DATE == "BIRTHDATE"){
            binding.etTanggalLahir.setText(date)
        }else if(viewModel.TAG_DATE == "HOME"){
            binding.etHomeTanggalMenempati.setText(date)
        }else if(viewModel.TAG_DATE == "SPOUSE"){
            binding.etTanggalLahirPasangan.setText(date)
        }
    }

    override fun onKodePosKeywordSubmited(keyword: String) {
        viewModel.loadKodePos(keyword)
    }

    override fun onKodePosSelected(data: KodePos) {
        if(viewModel.TAG_KODE_POS == "KTP"){
            binding.etKtpKodepos.setText(data.description)
            viewModel.selectedKodePosKTP = data
        }else if(viewModel.TAG_KODE_POS == "HOME"){
            binding.etHomeKodepos.setText(data.description)
            viewModel.selectedKodePosRumah = data
        }
        searchKodePosDialog?.dismiss()
    }

    override fun onDatillKeywordSubmited(keyword: String) {
        viewModel.loadLokasiDatill(keyword)
    }

    override fun onDatillSelected(data: LokasiDatill) {
        viewModel.selectedLokasiDatill = data
        binding.etDatill.setText(data.description)
        searchDatillDialog?.dismiss()
    }
}