package com.mobile.ewallet.feature.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.mobile.ewallet.R
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
            viewModel.profileData?.let { profileData ->
                startActivity(
                    Intent(this@ProfileActivity, UpdateProfileActivity::class.java)
                        .putExtra("PROFILE", Gson().toJson(profileData))
                )
            }
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

        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadProfile()
    }

    private fun observeViewModel(){
        viewModel.onProfileLoaded.observe(this){
            Glide.with(this)
                .load(it.photoProfileThumbnail)
                .placeholder(R.drawable.user_placeholder)
                .into(binding.image)
            binding.name.text = it.nama
            binding.phone.text = it.nOWA
        }
        viewModel.isProfileLoading.observe(this){
            if(it){
                binding.viewName.visibility = View.GONE
            }else{
                binding.viewName.visibility = View.VISIBLE
            }
        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}