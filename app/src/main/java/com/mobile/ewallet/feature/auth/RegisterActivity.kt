package com.mobile.ewallet.feature.auth

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityRegisterBinding

class RegisterActivity: BaseActivity<AuthViewModel>(), OTPDialog.OTPListener {

    override val viewModelClass: Class<AuthViewModel> get() = AuthViewModel::class.java
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        val prefixView = binding.tilPhone.findViewById<AppCompatTextView>(com.google.android.material.R.id.textinput_prefix_text)
        prefixView.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
        prefixView.gravity = Gravity.CENTER

        binding.topbar.actionBack.setOnClickListener { onBackPressed() }

        binding.btnSubmit.setOnClickListener {
            val otpDialog = OTPDialog().newInstance()
            otpDialog.listener = this@RegisterActivity
            otpDialog.isCancelable = true
            otpDialog.show(supportFragmentManager, null)
        }
    }

    override fun onInputComplete(otp: String) {
        startActivity(
            Intent(
                this@RegisterActivity,
                CreateAccountActivity::class.java
            )
        )
    }
}