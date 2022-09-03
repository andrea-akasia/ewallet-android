package com.mobile.ewallet.feature.profile

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityUpdateProfileBinding

class UpdateProfileActivity: BaseActivity<ProfileViewModel>() {

    override val viewModelClass: Class<ProfileViewModel> get() = ProfileViewModel::class.java
    private lateinit var binding: ActivityUpdateProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, android.R.color.white)
        window.insetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
        val prefixView =
            binding.tilPhone.findViewById<AppCompatTextView>(com.google.android.material.R.id.textinput_prefix_text)
        prefixView.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        prefixView.gravity = Gravity.CENTER

        binding.topbar.actionBack.setOnClickListener { onBackPressed() }
        binding.topbar.title.text = "Ubah Profile"

    }
}