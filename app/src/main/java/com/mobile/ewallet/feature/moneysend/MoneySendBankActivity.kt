package com.mobile.ewallet.feature.moneysend

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivitySendBankBinding
import com.mobile.ewallet.feature.pay.PayInputActivity

class MoneySendBankActivity: BaseActivity<SendMoneyViewModel>() {

    override val viewModelClass: Class<SendMoneyViewModel> get() = SendMoneyViewModel::class.java
    private lateinit var binding: ActivitySendBankBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendBankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressed() }
        binding.topbar.title.text = "Detail Bank Penerima"

        val bankAdapter = ArrayAdapter(this, R.layout.view_item_spinner_bank, viewModel.getDummyBank())
        binding.spinnerBank.adapter = bankAdapter

        binding.btnSubmit.setOnClickListener {
            validateForm()
        }

        observeViewModel()
        viewModel.loadBankList()
    }

    private fun validateForm(){
        if(viewModel.selectedBank == null){
            Toast.makeText(this, "bank belum dipilih", Toast.LENGTH_SHORT).show()
            return
        }
        if(binding.accountNumber.text.toString().isEmpty()){
            Toast.makeText(this, "nomor rekening harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.loadMinimumNominalTransferBank(
            idBank = viewModel.selectedBank!!.iD!!,
            accountNumber = binding.accountNumber.text.toString()
        )
    }

    private fun observeViewModel(){
        viewModel.onTransferBankMinimumNominalLoaded.observe(this){
            startActivity(
                Intent(this@MoneySendBankActivity, PayInputActivity::class.java)
                    .putExtra("ACTION", "BANK")
                    .putExtra("ID_BANK", viewModel.selectedBank!!.iD)
                    .putExtra("DATA", Gson().toJson(it))
            )
        }

        viewModel.onBankListLoaded.observe(this){
            ArrayAdapter(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                it
            ).also { bankAdapter ->
                bankAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                binding.spinnerBank.adapter = bankAdapter
                binding.spinnerBank.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        viewModel.selectedBank = viewModel.banks[position]
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