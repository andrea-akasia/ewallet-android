package com.mobile.ewallet.feature.credit

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityCreditInfoBinding
import com.mobile.ewallet.feature.credit.billing.BillingDetailActivity
import com.mobile.ewallet.feature.home.TransactionAdapter
import com.mobile.ewallet.feature.topup.TopupViaKreditActivity

class CreditDetailActivity: BaseActivity<CreditViewModel>() {

    override val viewModelClass: Class<CreditViewModel> get() = CreditViewModel::class.java
    private lateinit var binding: ActivityCreditInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreditInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, android.R.color.white)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
        }else{
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
            window.statusBarColor = Color.WHITE
        }

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.topbar.title.text = "Saldo Kredit"
        intent.getStringExtra("BALANCE")?.let {
            binding.balanceTotal.setText(it)
        }
        intent.getStringExtra("LIMIT")?.let {
            binding.valueLimit.text = it
        }

        binding.actionTopup.setOnClickListener {
            startActivity(
                Intent(this, TopupViaKreditActivity::class.java)
            )
        }

        binding.actionBilling.setOnClickListener {
            viewModel.billingData?.let { bc ->
                startActivity(
                    Intent(this, BillingDetailActivity::class.java)
                        .putExtra("DATA", Gson().toJson(bc))
                )
            }
        }

        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCreditHistoryTransaction()
        viewModel.loadBillingData()
    }

    private fun observeViewModel(){
        viewModel.onBillingAvailable.observe(this) {
            if(it){
                binding.viewBillingAvailable.visibility = View.VISIBLE
                binding.viewBillingUnavailable.visibility = View.GONE
            }else{
                binding.viewBillingAvailable.visibility = View.GONE
                binding.viewBillingUnavailable.visibility = View.VISIBLE
            }
        }

        viewModel.onCreditHistoryTransactionLoaded.observe(this){
            if(it.isNotEmpty()){
                binding.rv.visibility = View.VISIBLE
                binding.viewEmpty.visibility = View.GONE
                val trxAdapter = TransactionAdapter(it)
                binding.rv.layoutManager = LinearLayoutManager(this)
                binding.rv.adapter = trxAdapter
            }else{
                binding.rv.visibility = View.GONE
                binding.viewEmpty.visibility = View.VISIBLE
            }

        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}