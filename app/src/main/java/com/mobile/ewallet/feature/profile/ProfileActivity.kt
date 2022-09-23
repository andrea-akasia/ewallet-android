package com.mobile.ewallet.feature.profile

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityProfileBinding
import com.mobile.ewallet.feature.auth.AuthActivity

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
        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        binding.viewName.setOnClickListener {
            startActivity(
                Intent(
                    this@ProfileActivity,
                    UpdateProfileActivity::class.java
                )
            )
        }

        binding.btnLogout.setOnClickListener {
            val logoutDialog = AlertDialog.Builder(this)
            logoutDialog.setMessage("Anda yakin ingin keluar?")
            logoutDialog.setCancelable(false)
            logoutDialog.setNegativeButton("batal") { d, _ ->
                d.dismiss()
            }
            logoutDialog.setPositiveButton("Keluar") { d, _ ->
                viewModel.logout()
                startActivity(
                    Intent(this@ProfileActivity, AuthActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                )
                this@ProfileActivity.finish()
                d.dismiss()
            }
            logoutDialog.show()
        }
    }
}