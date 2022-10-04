package com.mobile.ewallet.feature.home

import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityBadgeBinding
import com.mobile.ewallet.util.GlideApp

class BadgeActivity: BaseActivity<HomeViewModel>() {

    override val viewModelClass: Class<HomeViewModel> get() = HomeViewModel::class.java
    private lateinit var binding: ActivityBadgeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBadgeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.topbar.title.text = "Badge Profile"

        observeViewModel()
        viewModel.loadBadgeStatus()
        viewModel.loadBadges()
    }

    private fun observeViewModel(){
        viewModel.onBadgeStatusLoaded.observe(this){
            GlideApp.with(this)
                .load(it.badgeImage)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.imgBadge)
            binding.balance.text = it.sisaSaldo
            binding.totalTopup.text = it.badgeTotalTopup
            binding.statusBadge.text = it.badge
        }

        viewModel.onBadgesLoaded.observe(this){
            val badgeAdapter = BadgeAdapter(it)
            binding.rv.layoutManager = LinearLayoutManager(this)
            binding.rv.adapter = badgeAdapter
        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}