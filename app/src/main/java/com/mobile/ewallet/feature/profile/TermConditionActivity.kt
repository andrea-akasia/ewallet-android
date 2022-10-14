package com.mobile.ewallet.feature.profile

import android.os.Bundle
import android.text.Html
import androidx.core.content.ContextCompat
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityTermsConditionsBinding

class TermConditionActivity: BaseActivity<ProfileViewModel>() {

    override val viewModelClass: Class<ProfileViewModel> get() = ProfileViewModel::class.java
    private lateinit var binding: ActivityTermsConditionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsConditionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.topbar.title.text = "Syarat & Ketentuan"

        intent.getStringExtra("DATA")?.let {
            binding.content.text = Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)
        }
    }
}