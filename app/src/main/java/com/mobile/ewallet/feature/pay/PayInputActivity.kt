package com.mobile.ewallet.feature.pay

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityPayInputBinding

class PayInputActivity: BaseActivity<PayViewModel>() {

    override val viewModelClass: Class<PayViewModel> get() = PayViewModel::class.java
    private lateinit var binding: ActivityPayInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressed() }
        binding.topbar.title.text = "Detail Pembayaran"
    }
}