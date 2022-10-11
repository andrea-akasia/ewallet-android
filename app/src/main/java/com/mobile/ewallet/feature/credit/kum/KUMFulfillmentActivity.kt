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
import com.mobile.ewallet.util.DatePickerFragment

class KUMFulfillmentActivity: BaseActivity<FulfillmentViewModel>() {

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

        observeViewModel()
        viewModel.loadFormKewarganegaraan()
    }

    private fun observeViewModel(){
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
}