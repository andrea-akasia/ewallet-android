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
import com.mobile.ewallet.databinding.ActivityKurFulfillmentBinding
import com.mobile.ewallet.feature.credit.DatillSearchDialog
import com.mobile.ewallet.feature.credit.KodePosSearchDialog
import com.mobile.ewallet.model.api.credit.KodePos
import com.mobile.ewallet.model.api.credit.LokasiDatill
import com.mobile.ewallet.util.DatePickerFragment
import com.mobile.ewallet.util.LoadingDialog

class KURFulfillmentActivity: BaseActivity<KURFulfillmentViewModel>(),
    DatePickerFragment.DateListener, KodePosSearchDialog.SearchKodePosListener,
    DatillSearchDialog.SearchDatillListener {

    override val viewModelClass: Class<KURFulfillmentViewModel> get() = KURFulfillmentViewModel::class.java
    private lateinit var binding: ActivityKurFulfillmentBinding

    private var searchKodePosDialog: KodePosSearchDialog? = null
    private var searchDatillDialog: DatillSearchDialog? = null
    private var loadingView: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKurFulfillmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.topbar.title.text = "KUR Fulfillment"

        intent.getStringExtra("ID_REQUEST")?.let { viewModel.creditRequestId = it}

        intent.getStringExtra("SELECTED_STATUS_KAWIN")?.let {
            if(it == "1"){
                binding.viewPernikahanOptional.visibility = View.VISIBLE
            }else{
                binding.viewPernikahanOptional.visibility = View.GONE
            }
        }

        binding.etBerdiriSejak.setOnClickListener {
            viewModel.TAG_DATE = "BERDIRI_SEJAK"
            val datePicker = DatePickerFragment()
            datePicker.isCancelable = true
            datePicker.listener = this@KURFulfillmentActivity
            datePicker.show(supportFragmentManager, null)
        }

        binding.etBekerjaSejak.setOnClickListener {
            viewModel.TAG_DATE = "BEKERJA_SEJAK"
            val datePicker = DatePickerFragment()
            datePicker.isCancelable = true
            datePicker.listener = this@KURFulfillmentActivity
            datePicker.show(supportFragmentManager, null)
        }

        binding.etKodeposKantor.setOnClickListener {
            viewModel.TAG_KODE_POS = "KANTOR"
            searchKodePosDialog = KodePosSearchDialog().newInstance()
            searchKodePosDialog?.listener = this@KURFulfillmentActivity
            searchKodePosDialog?.show(supportFragmentManager, null)
        }

        binding.etDatill.setOnClickListener {
            searchDatillDialog = DatillSearchDialog().newInstance()
            searchDatillDialog?.listener = this@KURFulfillmentActivity
            searchDatillDialog?.show(supportFragmentManager, null)
        }

        binding.etBekerjaTanggalMenikah.setOnClickListener {
            viewModel.TAG_DATE = "TANGGAL_MENIKAH"
            val datePicker = DatePickerFragment()
            datePicker.isCancelable = true
            datePicker.listener = this@KURFulfillmentActivity
            datePicker.show(supportFragmentManager, null)
        }

        binding.btnContinue.setOnClickListener {
            viewModel.submitFulfillmentKUR(
                phone = binding.etPhone.text.toString(),
                faxArea = binding.etFaxArea.text.toString(),
                fax = binding.etFax.text.toString(),
                berdiriSejak = binding.etBerdiriSejak.text.toString(),
                bekerjaSejak = binding.etBekerjaSejak.text.toString(),
                namaPerusahaan = binding.etNamaPerusahaan.text.toString(),
                alamatKantor1 = binding.etAlamatKantor1.text.toString(),
                alamatKantor2 = binding.etAlamatKantor2.text.toString(),
                alamatKantor3 = binding.etAlamatKantor3.text.toString(),
                kecamatanKantor = binding.etKecamatanKantor.text.toString(),
                kelurahanKantor = binding.etKelurahanKantor.text.toString(),
                faxAreaKantor = binding.etFaxAreaKantor.text.toString(),
                faxKantor = binding.etFaxKantor.text.toString(),
                telpAreaKantor = binding.etPhoneAreaKantor.text.toString(),
                telpKantor = binding.etPhoneKantor.text.toString(),
                emergencyName = binding.etEmergencyName.text.toString(),
                hubungan = binding.etHubungan.text.toString(),
                tanggalMenikah = binding.etBekerjaTanggalMenikah.text.toString(),
                luasLahan = binding.etLuasLahan.text.toString(),
                suratPermohonan = binding.etNoSuratPermohonan.text.toString(),
                ijinUsaha = binding.etNoIjinUsaha.text.toString()
            )
        }

        observeViewModel()
        viewModel.loadFormKewarganegaraan()
    }

    private fun observeViewModel(){
        viewModel.onKURPreviewLoaded.observe(this){ data ->
            data.kewarganegaraan?.let {
                binding.spinnerKewarganegaraan.setSelection(
                    viewModel.kewarganegaraans.indexOfFirst { it.code == data.kewarganegaraan }
                )
            }
            binding.etPhone.setText(data.telpHP)
            binding.etFaxArea.setText(data.faxArea)
            binding.etFax.setText(data.fax)

            data.profesi?.let {
                binding.spinnerProfesi.setSelection(
                    viewModel.profesis.indexOfFirst { it.code == data.profesi }
                )
            }
            data.jabatan?.let {
                binding.spinnerJabatan.setSelection(
                    viewModel.jabatans.indexOfFirst { it.code == data.jabatan }
                )
            }
            data.bidangUsaha?.let {
                binding.spinnerBidangUsaha.setSelection(
                    viewModel.bidangUsahas.indexOfFirst { it.code == data.bidangUsaha }
                )
            }
            data.tempatBekerja?.let {
                binding.spinnerTempatBekerja.setSelection(
                    viewModel.tempatBekerjas.indexOfFirst { it.code == data.tempatBekerja }
                )
            }
            binding.etBerdiriSejak.setText(data.berdiriSejak)
            binding.etBekerjaSejak.setText(data.bekerjausahasejak)

            binding.etNamaPerusahaan.setText(data.namaPerusahaan)
            binding.etAlamatKantor1.setText(data.alamatKantorLine1)
            binding.etAlamatKantor2.setText(data.alamatKantorLine2)
            binding.etAlamatKantor3.setText(data.alamatKantorLine3)
            binding.etKecamatanKantor.setText(data.kecamatanKantor)
            binding.etKelurahanKantor.setText(data.kelurahanKantor)
            binding.etKodeposKantor.setText(data.kodePosKantorTEXT)
            data.kodePosKantor?.let { viewModel.selectedKodePosKantor = KodePos().apply { code = it } }
            binding.etFaxAreaKantor.setText(data.noFaxArea)
            binding.etFaxKantor.setText(data.noFax)
            binding.etPhoneAreaKantor.setText(data.noTelpArea)
            binding.etPhoneKantor.setText(data.noTelp)

            binding.etEmergencyName.setText(data.namaEmergencyContact)
            binding.etHubungan.setText(data.hubungan)

            data.profesiPasangan?.let {
                if(it.isNotEmpty()){
                    binding.spinnerProfesiPasangan.setSelection(
                        viewModel.profesis.indexOfFirst { pp -> pp.code == data.profesiPasangan }
                    )
                }
            }
            data.tempatBekerjaPasangan?.let {
                if(it.isNotEmpty()){
                    binding.spinnerTempatBekerjaPasangan.setSelection(
                        viewModel.tempatBekerjas.indexOfFirst { tbp -> tbp.code == data.tempatBekerjaPasangan }
                    )
                }
            }
            data.bidangUsahaPasangan?.let {
                if(it.isNotEmpty()){
                    binding.spinnerBidangUsahaPasangan.setSelection(
                        viewModel.bidangUsahas.indexOfFirst { bup -> bup.code == data.bidangUsahaPasangan }
                    )
                }
            }
            binding.etBekerjaTanggalMenikah.setText(data.mulaiBekerjaTanggalMenikah)
            binding.etDatill.setText(data.lokasiDatiIIUsahaTEXT)
            data.lokasiDatiIIUsaha?.let { viewModel.selectedLokasiDatill = LokasiDatill().apply { code = it } }

            data.sumberDana?.let {
                if(it.isNotEmpty()){
                    binding.spinnerSumberDana.setSelection(
                        viewModel.sumberDanas.indexOfFirst { sd -> sd.code == data.sumberDana }
                    )
                }
            }
            data.komoditas?.let {
                if(it.isNotEmpty()){
                    binding.spinnerKomoditas.setSelection(
                        viewModel.komoditass.indexOfFirst { k -> k.code == data.komoditas }
                    )
                }
            }
            data.jenisDebitur?.let {
                if(it.isNotEmpty()){
                    binding.spinnerJenisDebitur.setSelection(
                        viewModel.jenisDebiturs.indexOfFirst { jd -> jd.code == data.jenisDebitur }
                    )
                }
            }
            binding.etLuasLahan.setText(data.luasLahan)
            binding.etNoSuratPermohonan.setText(data.noSurat)
            binding.etNoIjinUsaha.setText(data.noIjinUsaha)

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

        viewModel.onFulfillmentSuccess.observe(this){
            startActivity(
                Intent(this, KURUploadDocumentsActivity::class.java)
                    .putExtra("ID_REQUEST", viewModel.creditRequestId)
            )
        }

        viewModel.onFormJenisDebiturLoaded.observe(this){
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it).also { adptr ->
                adptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                binding.spinnerJenisDebitur.adapter = adptr
                binding.spinnerJenisDebitur.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if(position > 0){
                            viewModel.selectedJenisDebitur = viewModel.jenisDebiturs[position]
                        }else{
                            viewModel.selectedJenisDebitur = null
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
        }

        viewModel.onFormKomoditasLoaded.observe(this){
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it).also { adptr ->
                adptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                binding.spinnerKomoditas.adapter = adptr
                binding.spinnerKomoditas.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if(position > 0){
                            viewModel.selectedKomoditas = viewModel.komoditass[position]
                        }else{
                            viewModel.selectedKomoditas = null
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
        }

        viewModel.onFormSumberDanaLoaded.observe(this){
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it).also { adptr ->
                adptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                binding.spinnerSumberDana.adapter = adptr
                binding.spinnerSumberDana.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if(position > 0){
                            viewModel.selectedSumberDana = viewModel.sumberDanas[position]
                        }else{
                            viewModel.selectedSumberDana = null
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

        viewModel.onFormTempatBekerjaLoaded.observe(this){
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it).also { adptr ->
                adptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                binding.spinnerTempatBekerja.adapter = adptr
                binding.spinnerTempatBekerja.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if(position > 0){
                            viewModel.selectedTempatBekerja = viewModel.tempatBekerjas[position]
                        }else{
                            viewModel.selectedTempatBekerja = null
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }

            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it).also { adptr ->
                adptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                binding.spinnerTempatBekerjaPasangan.adapter = adptr
                binding.spinnerTempatBekerjaPasangan.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if(position > 0){
                            viewModel.selectedTempatBekerjaPasangan = viewModel.tempatBekerjas[position]
                        }else{
                            viewModel.selectedTempatBekerjaPasangan = null
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
        }

        viewModel.onFormBidangUsahaLoaded.observe(this){
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it).also { adptr ->
                adptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                binding.spinnerBidangUsaha.adapter = adptr
                binding.spinnerBidangUsaha.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if(position > 0){
                            viewModel.selectedBidangUsaha = viewModel.bidangUsahas[position]
                        }else{
                            viewModel.selectedBidangUsaha = null
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }

            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it).also { adptr ->
                adptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                binding.spinnerBidangUsahaPasangan.adapter = adptr
                binding.spinnerBidangUsahaPasangan.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if(position > 0){
                            viewModel.selectedBidangUsahaPasangan = viewModel.bidangUsahas[position]
                        }else{
                            viewModel.selectedBidangUsahaPasangan = null
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
        }

        viewModel.onFormJabatanLoaded.observe(this){
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it).also { adptr ->
                adptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                binding.spinnerJabatan.adapter = adptr
                binding.spinnerJabatan.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if(position > 0){
                            viewModel.selectedJabatan = viewModel.jabatans[position]
                        }else{
                            viewModel.selectedJabatan = null
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
        }

        viewModel.onFormProfesiLoaded.observe(this){
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it).also { adptr ->
                adptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                binding.spinnerProfesi.adapter = adptr
                binding.spinnerProfesi.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if(position > 0){
                            viewModel.selectedProfesi = viewModel.profesis[position]
                        }else{
                            viewModel.selectedProfesi = null
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }

            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it).also { adptr ->
                adptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                binding.spinnerProfesiPasangan.adapter = adptr
                binding.spinnerProfesiPasangan.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if(position > 0){
                            viewModel.selectedProfesiPasangan = viewModel.profesis[position]
                        }else{
                            viewModel.selectedProfesiPasangan = null
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
        }

        viewModel.onFormKewarganegaraanLoaded.observe(this){
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it).also { adptr ->
                adptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                binding.spinnerKewarganegaraan.adapter = adptr
                binding.spinnerKewarganegaraan.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if(position > 0){
                            viewModel.selectedKewarganegaraan = viewModel.kewarganegaraans[position]
                        }else{
                            viewModel.selectedKewarganegaraan = null
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
        if(viewModel.TAG_DATE == "BERDIRI_SEJAK"){
            binding.etBerdiriSejak.setText(date)
        }else if(viewModel.TAG_DATE == "BEKERJA_SEJAK"){
            binding.etBekerjaSejak.setText(date)
        }else if(viewModel.TAG_DATE == "TANGGAL_MENIKAH"){
            binding.etBekerjaTanggalMenikah.setText(date)
        }
    }

    override fun onKodePosKeywordSubmited(keyword: String) {
        viewModel.loadKodePos(keyword)
    }

    override fun onKodePosSelected(data: KodePos) {
        if(viewModel.TAG_KODE_POS == "KANTOR"){
            binding.etKodeposKantor.setText(data.description)
            viewModel.selectedKodePosKantor = data
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