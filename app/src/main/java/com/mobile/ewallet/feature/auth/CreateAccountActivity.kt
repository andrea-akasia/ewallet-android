package com.mobile.ewallet.feature.auth

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityCreateAccountBinding

class CreateAccountActivity: BaseActivity<AuthViewModel>() {

    override val viewModelClass: Class<AuthViewModel> get() = AuthViewModel::class.java
    private lateinit var binding: ActivityCreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
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

        binding.btnSubmit.setOnClickListener {

        }
    }
}