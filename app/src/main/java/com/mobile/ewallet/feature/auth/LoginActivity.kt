package com.mobile.ewallet.feature.auth

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityLoginBinding
import com.mobile.ewallet.feature.home.HomeActivity
import com.mobile.ewallet.util.DeviceUuidFactory

class LoginActivity: BaseActivity<AuthViewModel>(), OTPDialog.OTPListener {

    override val viewModelClass: Class<AuthViewModel> get() = AuthViewModel::class.java
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        val prefixView = binding.tilPhone.findViewById<AppCompatTextView>(com.google.android.material.R.id.textinput_prefix_text)
        prefixView.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
        prefixView.gravity = Gravity.CENTER

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        binding.btnSubmit.setOnClickListener {
            validateForm()
        }

        observeViewModel()
    }

    private fun validateForm(){
        if(binding.etPhone.text.toString().isNotEmpty()){
            if(binding.etPhone.text.toString().substring(0, 1) == "8"){
                viewModel.requestOTPLogin(
                    phone = "0${binding.etPhone.text}",
                    uuid = DeviceUuidFactory(applicationContext).getDeviceUuid().toString()
                )
            }else{
                Toast.makeText(this, "format inputan no hp belum benar", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "no hp tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onInputComplete(otp: String) {
        viewModel.confirmOTPLogin(
            phone = "0${binding.etPhone.text}",
            uuid = DeviceUuidFactory(applicationContext).getDeviceUuid().toString(),
            otp = otp
        )
    }

    override fun onResendTrigger() {
        viewModel.resendOTPLogin(
            phone = "0${binding.etPhone.text}",
            uuid = DeviceUuidFactory(applicationContext).getDeviceUuid().toString()
        )
    }

    private fun observeViewModel(){
        viewModel.onConfirmOTPSuccess.observe(this){
            startActivity(
                Intent(this@LoginActivity, HomeActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
            this@LoginActivity.finish()
        }

        viewModel.onResendOTPLoginSuccess.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.onReqOTPLoginSuccess.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            val otpDialog = OTPDialog().newInstance("0${binding.etPhone.text}")
            otpDialog.listener = this@LoginActivity
            otpDialog.isCancelable = true
            otpDialog.show(supportFragmentManager, null)
        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}