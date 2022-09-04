package com.mobile.ewallet.feature.moneysend

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
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
            startActivity(
                Intent(this@MoneySendBankActivity, PayInputActivity::class.java)
            )
        }
    }
}