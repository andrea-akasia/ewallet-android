package com.mobile.ewallet.feature.moneysend

import android.content.Intent
import android.os.Bundle
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

        binding.topbar.actionBack.setOnClickListener { onBackPressed() }
        binding.topbar.title.text = "Kirim Uang"

        historyAdapter = HistoryAdapter(viewModel.getDummy())
        binding.rv.layoutManager = GridLayoutManager(this, 3)
        binding.rv.adapter = historyAdapter

        binding.actionFromBank.setOnClickListener {
            startActivity(
                Intent(this@MoneySendTypeActivity, MoneySendBankActivity::class.java)
            )
        }
    }
}