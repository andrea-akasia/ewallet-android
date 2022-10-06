package com.mobile.ewallet.feature.moneysend

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivitySendTypeBinding

class MoneySendTypeActivity: BaseActivity<SendMoneyViewModel>() {

    override val viewModelClass: Class<SendMoneyViewModel> get() = SendMoneyViewModel::class.java
    private lateinit var binding: ActivitySendTypeBinding

    private var historyAdapter: HistoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.topbar.title.text = "Kirim Uang"

        binding.actionFromBank.setOnClickListener {
            startActivity(
                Intent(this@MoneySendTypeActivity, MoneySendBankActivity::class.java)
            )
        }

        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadHistoryTransfer()
    }

    private fun observeViewModel(){
        viewModel.onHistoryTransactionLoaded.observe(this){
            if(it.isNotEmpty()){
                binding.emptyView.visibility = View.GONE
                binding.rv.visibility = View.VISIBLE
                historyAdapter = HistoryAdapter(it)
                binding.rv.layoutManager = GridLayoutManager(this, 3)
                binding.rv.adapter = historyAdapter
            }else{
                binding.emptyView.visibility = View.VISIBLE
                binding.rv.visibility = View.GONE
            }
        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}