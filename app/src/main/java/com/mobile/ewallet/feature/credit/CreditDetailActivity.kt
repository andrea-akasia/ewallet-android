package com.mobile.ewallet.feature.credit

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityCreditInfoBinding
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
        window.insetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)

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

        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCreditHistoryTransaction()
    }

    private fun observeViewModel(){
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