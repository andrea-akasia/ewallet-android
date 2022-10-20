package com.mobile.ewallet.feature.credit.billing

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityBillingDetailBinding
import com.mobile.ewallet.feature.topup.VirtualAccAdapter
import com.mobile.ewallet.model.api.credit.billing.BillingCredit
import com.mobile.ewallet.model.api.credit.billing.BillingVA
import com.mobile.ewallet.util.formatToCurrency

class BillingDetailActivity: BaseActivity<BillingViewModel>(), BillingVAAdapter.BillingVAListener {

    override val viewModelClass: Class<BillingViewModel> get() = BillingViewModel::class.java
    private lateinit var binding: ActivityBillingDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBillingDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.topbar.title.text = "Bayar Tagihan Pinjaman"


        observeViewModel()
        intent.getStringExtra("DATA")?.let {
            viewModel.billingData = Gson().fromJson(it, BillingCredit::class.java)
            binding.totalInvoice.text = viewModel.billingData.totalTagihan.formatToCurrency()
            binding.valueCreditActive.text = viewModel.billingData.pinjamanAktif.formatToCurrency()
            binding.valueDeadline.text = viewModel.billingData.tanggalJatuhTempo
            binding.valueBunga.text = viewModel.billingData.bunga.formatToCurrency()
            binding.valueDenda.text = viewModel.billingData.dendaKeterlambatan.formatToCurrency()

            viewModel.loadBillingDetail(viewModel.billingData.iD)
        }

        viewModel.loadBillingHistory()
    }

    private fun observeViewModel(){
        viewModel.onBillingHistoryLoaded.observe(this){
            if(it.isNotEmpty()){
                val historyAdapter = BillingHistoryAdapter(it)
                binding.rvTrx.layoutManager = LinearLayoutManager(this@BillingDetailActivity)
                binding.rvTrx.adapter = historyAdapter
            }else{
                binding.viewEmptyTrx.visibility = View.VISIBLE
                binding.rvTrx.visibility = View.GONE
            }
        }

        viewModel.onBillingDetailLoaded.observe(this){
            if(it.isNotEmpty()){
                val vaAdapter = BillingVAAdapter(it, this@BillingDetailActivity)
                binding.rv.layoutManager = LinearLayoutManager(this@BillingDetailActivity)
                binding.rv.adapter = vaAdapter
            }else{
                Toast.makeText(this, "virtual account data is empty", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onVASelected(data: BillingVA) {

    }
}