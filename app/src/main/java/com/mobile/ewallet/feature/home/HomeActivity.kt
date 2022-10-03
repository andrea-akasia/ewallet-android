package com.mobile.ewallet.feature.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityHomeBinding
import com.mobile.ewallet.feature.auth.AuthActivity
import com.mobile.ewallet.feature.moneyreq.MoneyRequestActivity
import com.mobile.ewallet.feature.moneysend.MoneySendTypeActivity
import com.mobile.ewallet.feature.pay.PayInputActivity
import com.mobile.ewallet.feature.profile.ProfileActivity
import com.mobile.ewallet.util.GlideApp

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

        binding.actionProfile.setOnClickListener {
            startActivity(
                Intent(
                    this@HomeActivity,
                    ProfileActivity::class.java
                )
            )
        }

        binding.viewBadge.setOnClickListener {
            startActivity(
                Intent(
                    this@HomeActivity,
                    BadgeActivity::class.java
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

        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        if(!viewModel.isLoggedIn()){
            startActivity(
                Intent(this@HomeActivity, AuthActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
            this@HomeActivity.finish()
        }else{
            viewModel.loadProfile()
            viewModel.loadDashboardBalance()
            viewModel.loadHistoryTransaction()
        }
    }

    private fun observeViewModel(){
        viewModel.onHistoryTransactionLoaded.observe(this){
            adapter = TransactionAdapter(it)
            binding.rv.layoutManager = LinearLayoutManager(this)
            binding.rv.adapter = adapter
        }

        viewModel.onDashboardBalanceLoaded.observe(this){
            GlideApp.with(this)
                .load(it.badgeImage)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.imgBadge)
            binding.balanceTotal.text = it.sisaSaldo
            binding.valueLastTopup.text = "+${it.lastTopup}"
            binding.statusBadge.text = it.badge
            binding.balanceIn.text = it.dATAIN
            binding.balanceOut.text = it.dATAOUT

            it.iDPendanaanDisetujui?.let { approvedPendanaanId ->
                if(approvedPendanaanId != "0"){
                    //show credit info
                    binding.viewCreditReqPrompt.visibility = View.GONE
                    binding.viewCreditInfo.visibility = View.VISIBLE
                    binding.valueLimitCredit.text = it.limitPinjaman
                    binding.valueActiveCredit.text = it.pinjamanAktif
                }else{
                    binding.viewCreditInfo.visibility = View.GONE
                    binding.viewCreditReqPrompt.visibility = View.VISIBLE
                }
            }
        }

        viewModel.onProfileLoaded.observe(this){
            Glide.with(this)
                .load(it.photoProfileThumbnail)
                .placeholder(R.drawable.user_placeholder)
                .into(binding.userImage)
        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}