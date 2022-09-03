package com.mobile.ewallet.feature.profile

import android.content.Intent
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.core.content.ContextCompat
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityProfileBinding

class ProfileActivity: BaseActivity<ProfileViewModel>() {

    override val viewModelClass: Class<ProfileViewModel> get() = ProfileViewModel::class.java
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, android.R.color.white)
        window.insetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)

        binding.topbar.title.text = "Profile Pengguna"
        binding.topbar.actionBack.setOnClickListener { onBackPressed() }

        binding.actionUpdateProfile.setOnClickListener {
            startActivity(
                Intent(
                    this@ProfileActivity,
                    UpdateProfileActivity::class.java
                )
            )
        }
    }
}