package com.mobile.ewallet.feature.home

import android.content.Intent
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityHomeBinding
import com.mobile.ewallet.feature.moneyreq.MoneyRequestActivity
import com.mobile.ewallet.feature.moneysend.MoneySendTypeActivity
import com.mobile.ewallet.feature.pay.PayInputActivity
import com.mobile.ewallet.feature.profile.ProfileActivity

class HomeActivity: BaseActivity<HomeViewModel>() {

    override val viewModelClass: Class<HomeViewModel> get() = HomeViewModel::class.java
    private lateinit var binding: ActivityHomeBinding

    private var adapter: TransactionAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, android.R.color.white)
        window.insetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)

        adapter = TransactionAdapter(viewModel.dummyData())
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = adapter

        binding.actionProfile.setOnClickListener {
            startActivity(
                Intent(
                    this@HomeActivity,
                    ProfileActivity::class.java
                )
            )
        }

        binding.actionScan.setOnClickListener {
            startActivity(
                Intent(this@HomeActivity, PayInputActivity::class.java)
            )
        }

        binding.actionMoneyReq.setOnClickListener {
            startActivity(
                Intent(this@HomeActivity, MoneyRequestActivity::class.java)
            )
        }

        binding.actionSend.setOnClickListener {
            startActivity(
                Intent(this@HomeActivity, MoneySendTypeActivity::class.java)
            )
        }
    }
}