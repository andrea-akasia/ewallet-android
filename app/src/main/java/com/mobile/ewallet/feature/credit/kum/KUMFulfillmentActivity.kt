package com.mobile.ewallet.feature.credit.kum

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityKumFulfillmentBinding
import com.mobile.ewallet.feature.credit.KodePosSearchDialog
import com.mobile.ewallet.model.api.credit.KodePos
import com.mobile.ewallet.util.DatePickerFragment
import com.mobile.ewallet.util.getMaxDateForBirthDate

class KUMFulfillmentActivity: BaseActivity<FulfillmentViewModel>(),
    DatePickerFragment.DateListener, KodePosSearchDialog.SearchKodePosListener {

    override val viewModelClass: Class<FulfillmentViewModel> get() = FulfillmentViewModel::class.java
    private lateinit var binding: ActivityKumFulfillmentBinding

    private var searchKodePosDialog: KodePosSearchDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKumFulfillmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.topbar.title.text = "KUM Fulfillment"

        intent.getStringExtra("ID_REQUEST")?.let { viewModel.creditRequestId = it}

        binding.etBerdiriSejak.setOnClickListener {
            viewModel.TAG_DATE = "BERDIRI_SEJAK"
            val datePicker = DatePickerFragment()
            datePicker.isCancelable = true
            datePicker.listener = this@KUMFulfillmentActivity
            datePicker.show(supportFragmentManager, null)
        }

        binding.etBekerjaSejak.setOnClickListener {
            viewModel.TAG_DATE = "BEKERJA_SEJAK"
            val datePicker = DatePickerFragment()
            datePicker.isCancelable = true
            datePicker.listener = this@KUMFulfillmentActivity
            datePicker.show(supportFragmentManager, null)
        }

        binding.etKodeposKantor.setOnClickListener {
            viewModel.TAG_KODE_POS = "KANTOR"
            searchKodePosDialog = KodePosSearchDialog().newInstance()
            searchKodePosDialog?.listener = this@KUMFulfillmentActivity
            searchKodePosDialog?.show(supportFragmentManager, null)
        }

        binding.etBekerjaTanggalMenikah.setOnClickListener {
            viewModel.TAG_DATE = "TANGGAL_MENIKAH"
            val datePicker = DatePickerFragment()
            datePicker.isCancelable = true
            datePicker.listener = this@KUMFulfillmentActivity
            datePicker.show(supportFragmentManager, null)
        }

        observeViewModel()
        viewModel.loadFormKewarganegaraan()
    }

    private fun observeViewModel(){
        viewModel.onFormLokasiDatillLoaded.observe(this){
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it).also { adptr ->
                adptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                binding.spinnerDatill.adapter = adptr
                binding.spinnerDatill.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if(position > 0){
                            viewModel.selectedLokasiDatill = viewModel.lokasiDatills[position]
                        }else{
                            viewModel.selectedLokasiDatill = null
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
        }

        viewModel.onFormStatusPernikahanLoaded.observe(this){
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it).also { adptr ->
                adptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                binding.spinnerHubungan.adapter = adptr
                binding.spinnerHubungan.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
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
                            binding.viewPernikahanOptional.visibility = View.GONE
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
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
}