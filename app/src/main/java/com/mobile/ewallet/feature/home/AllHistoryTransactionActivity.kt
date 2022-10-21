package com.mobile.ewallet.feature.home

import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityAllTransactionBinding

class AllHistoryTransactionActivity: BaseActivity<HomeViewModel>() {

    override val viewModelClass: Class<HomeViewModel> get() = HomeViewModel::class.java
    private lateinit var binding: ActivityAllTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.topbar.title.text = "Riwayat Transaksi"

        observeViewModel()
        viewModel.loadAllHistoryTransaction()
    }

    private fun observeViewModel(){
        viewModel.onAllHistoryTransactionLoaded.observe(this){
            binding.rv.layoutManager = LinearLayoutManager(this)
            binding.rv.adapter = TransactionAdapter(it)
        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}