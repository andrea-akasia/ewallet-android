package com.mobile.ewallet.feature.credit.kum

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityKumPrescreeningBinding
import com.mobile.ewallet.feature.credit.CreditViewModel
import com.mobile.ewallet.feature.credit.KodePosSearchDialog
import com.mobile.ewallet.model.api.credit.KodePos
import com.mobile.ewallet.util.DatePickerFragment
import com.mobile.ewallet.util.getMaxDateForBirthDate

class KUMPrescreeningActivity: BaseActivity<CreditViewModel>(), DatePickerFragment.DateListener,
    KodePosSearchDialog.SearchKodePosListener {

    override val viewModelClass: Class<CreditViewModel> get() = CreditViewModel::class.java
    private lateinit var binding: ActivityKumPrescreeningBinding

    private var searchKodePosDialog: KodePosSearchDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKumPrescreeningBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.topbar.title.text = "KUM Pre Screening"

        observeViewModel()
        viewModel.loadFormJenisKelamin()

        binding.etTanggalLahir.setOnClickListener {
            viewModel.TAG_DATE = "BIRTHDATE"
            val datePicker = DatePickerFragment(maxDate = getMaxDateForBirthDate())
            datePicker.isCancelable = true
            datePicker.listener = this@KUMPrescreeningActivity
            datePicker.show(supportFragmentManager, null)
        }

        binding.etKtpKodepos.setOnClickListener {
            viewModel.TAG_KODE_POS = "KTP"
            searchKodePosDialog = KodePosSearchDialog().newInstance()
            searchKodePosDialog?.listener = this@KUMPrescreeningActivity
            searchKodePosDialog?.show(supportFragmentManager, null)
        }

        binding.etHomeKodepos.setOnClickListener {
            viewModel.TAG_KODE_POS = "HOME"
            searchKodePosDialog = KodePosSearchDialog().newInstance()
            searchKodePosDialog?.listener = this@KUMPrescreeningActivity
            searchKodePosDialog?.show(supportFragmentManager, null)
        }

        binding.etHomeTanggalMenempati.setOnClickListener {
            viewModel.TAG_DATE = "HOME"
            val datePicker = DatePickerFragment()
            datePicker.isCancelable = true
            datePicker.listener = this@KUMPrescreeningActivity
            datePicker.show(supportFragmentManager, null)
        }

        binding.etTanggalLahirPasangan.setOnClickListener {
            viewModel.TAG_DATE = "SPOUSE"
            val datePicker = DatePickerFragment(maxDate = getMaxDateForBirthDate())
            datePicker.isCancelable = true
            datePicker.listener = this@KUMPrescreeningActivity
            datePicker.show(supportFragmentManager, null)
        }
    }

    private fun observeViewModel(){
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
}