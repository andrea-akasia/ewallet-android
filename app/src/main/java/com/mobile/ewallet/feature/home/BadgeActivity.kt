package com.mobile.ewallet.feature.home

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityBadgeBinding

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
    }
}