package com.mobile.ewallet.feature.pay

import android.content.Intent
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityPayResultBinding
import com.mobile.ewallet.feature.home.HomeActivity
import com.mobile.ewallet.feature.home.TransactionDetailActivity
import com.mobile.ewallet.model.api.sendmoney.byscan.SendMoneyResult
import com.mobile.ewallet.util.formatToCurrency

class PayResultActivity: BaseActivity<PayViewModel>() {

    override val viewModelClass: Class<PayViewModel> get() = PayViewModel::class.java
    private lateinit var binding: ActivityPayResultBinding

    private var transactionId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, android.R.color.white)
        window.insetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)

        intent.getStringExtra("DATA")?.let {
            val data = Gson().fromJson(it, SendMoneyResult::class.java)
            transactionId = data.idTransaction
            binding.total.text = data.total.formatToCurrency()
            binding.time.text = data.time
            binding.type.text = data.transactionType
            binding.method.text = data.metodeBayar
            binding.reff.text  =data.reffNumber
        }

        binding.actionClose.setOnClickListener {
            startActivity(
                Intent(
                    this@PayResultActivity,
                    HomeActivity::class.java
                )
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
            this@PayResultActivity.finish()
        }

        binding.btnDetail.setOnClickListener {
            transactionId?.let { id ->
                startActivity(
                    Intent(this@PayResultActivity, TransactionDetailActivity::class.java)
                        .putExtra("ID", id)
                        .putExtra("ACTION", intent.getStringExtra("ACTION"))
                )
            }
        }
    }
}