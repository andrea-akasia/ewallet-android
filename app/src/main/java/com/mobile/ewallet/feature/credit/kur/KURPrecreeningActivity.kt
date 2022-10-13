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
import com.mobile.ewallet.feature.credit.KodePosSearchDialog
import com.mobile.ewallet.model.api.credit.KodePos
import com.mobile.ewallet.util.DatePickerFragment
import com.mobile.ewallet.util.getMaxDateForBirthDate

class KURPrecreeningActivity: BaseActivity<KURCreditViewModel>(), DatePickerFragment.DateListener,
    KodePosSearchDialog.SearchKodePosListener {

    override val viewModelClass: Class<KURCreditViewModel> get() = KURCreditViewModel::class.java
    private lateinit var binding: ActivityKurPrescreeningBinding

    private var searchKodePosDialog: KodePosSearchDialog? = null

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
        viewModel.onPrescreeningSuccess.observe(this){
            startActivity(
                Intent(this, KURFulfillmentActivity::class.java)
                    .putExtra("ID_REQUEST", viewModel.creditRequestId)
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