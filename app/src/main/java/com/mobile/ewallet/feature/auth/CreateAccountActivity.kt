package com.mobile.ewallet.feature.auth

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.OnBackPressedDispatcher
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

        binding.topbar.actionBack.setOnClickListener { OnBackPressedDispatcher().onBackPressed() }

        intent.getStringExtra("PHONE")?.let { phone ->
            binding.etPhone.setText(phone.substring(1, phone.length))
            binding.btnSubmit.setOnClickListener {
                validateForm(phone)
            }
        }

        observeViewModel()
    }

    private fun validateForm(phone: String){
        if(binding.etName.text.toString().isEmpty()){
            Toast.makeText(this, "Nama harus diisi", Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.finishRegister(
            phone = phone,
            fullName = binding.etName.text.toString()
        )
    }

    private fun observeViewModel(){
        viewModel.onRegisterFinished.observe(this){
            startActivity(
                Intent(this@CreateAccountActivity, StartupActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}