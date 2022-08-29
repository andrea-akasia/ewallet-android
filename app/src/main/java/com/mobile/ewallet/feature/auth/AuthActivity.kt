package com.mobile.ewallet.feature.auth

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityAuthBinding

class AuthActivity: BaseActivity<AuthViewModel>() {

    override val viewModelClass: Class<AuthViewModel> get() = AuthViewModel::class.java
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.btnLogin.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )
        }
    }
}