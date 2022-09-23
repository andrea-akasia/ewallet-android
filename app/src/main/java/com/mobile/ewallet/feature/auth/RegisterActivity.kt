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
import com.mobile.ewallet.databinding.ActivityRegisterBinding
import com.mobile.ewallet.util.DeviceUuidFactory

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

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        binding.btnSubmit.setOnClickListener {
            validateForm()
        }

        observeViewModel()
    }

    private fun validateForm(){
        if(binding.etPhone.text.toString().isNotEmpty()){
            if(binding.etPhone.text.toString().substring(0, 1) == "8"){
                viewModel.requestOTPRegister(
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
        viewModel.confirmOTPRegister(
            phone = "0${binding.etPhone.text}",
            uuid = DeviceUuidFactory(applicationContext).getDeviceUuid().toString(),
            otp = otp
        )

    }

    override fun onResendTrigger() {
        viewModel.resendOTPRegister(
            phone = "0${binding.etPhone.text}",
            uuid = DeviceUuidFactory(applicationContext).getDeviceUuid().toString()
        )
    }

    private fun observeViewModel(){
        viewModel.onConfirmOTPSuccess.observe(this){
            startActivity(
                Intent(this@RegisterActivity, CreateAccountActivity::class.java)
                    .putExtra("PHONE", "0${binding.etPhone.text}")
            )
        }

        viewModel.onResendOTPRegisterSuccess.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.onReqOTPRegisterSuccess.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            val otpDialog = OTPDialog().newInstance("0${binding.etPhone.text}")
            otpDialog.listener = this@RegisterActivity
            otpDialog.isCancelable = true
            otpDialog.show(supportFragmentManager, null)
        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}