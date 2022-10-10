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

class KUMPrescreeningActivity: BaseActivity<CreditViewModel>() {

    override val viewModelClass: Class<CreditViewModel> get() = CreditViewModel::class.java
    private lateinit var binding: ActivityKumPrescreeningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKumPrescreeningBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.topbar.title.text = "KUM Pre Screening"

        observeViewModel()
        viewModel.loadFormJenisKelamin()
    }

    private fun observeViewModel(){
        viewModel.onFormJenisKelaminLoaded.observe(this){
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it).also { bankAdapter ->
                bankAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                binding.spinnerJenisKelamin.adapter = bankAdapter
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
}