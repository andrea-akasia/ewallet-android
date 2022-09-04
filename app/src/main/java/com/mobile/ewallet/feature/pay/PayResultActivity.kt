package com.mobile.ewallet.feature.pay

import android.content.Intent
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.core.content.ContextCompat
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityPayResultBinding
import com.mobile.ewallet.feature.home.HomeActivity
import com.mobile.ewallet.feature.home.TransactionDetailActivity

class PayResultActivity: BaseActivity<PayViewModel>() {

    override val viewModelClass: Class<PayViewModel> get() = PayViewModel::class.java
    private lateinit var binding: ActivityPayResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, android.R.color.white)
        window.insetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)

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
            startActivity(
                Intent(this@PayResultActivity, TransactionDetailActivity::class.java)
            )
        }
    }
}