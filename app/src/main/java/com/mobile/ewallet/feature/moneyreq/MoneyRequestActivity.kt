package com.mobile.ewallet.feature.moneyreq

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityMoneyRequestBinding

class MoneyRequestActivity: BaseActivity<MoneyRequestViewModel>() {

    override val viewModelClass: Class<MoneyRequestViewModel> get() = MoneyRequestViewModel::class.java
    private lateinit var binding: ActivityMoneyRequestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoneyRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressed() }
        binding.topbar.title.text = "Minta Uang"
    }
}