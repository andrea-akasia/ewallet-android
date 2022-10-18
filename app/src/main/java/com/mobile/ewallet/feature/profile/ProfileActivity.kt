package com.mobile.ewallet.feature.profile

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityProfileBinding
import com.mobile.ewallet.feature.auth.AuthActivity
import com.mobile.ewallet.feature.auth.SplashcreenActivity
import com.mobile.ewallet.feature.credit.CreditDetailActivity
import com.mobile.ewallet.model.api.dashboard.DashboardBalance

class ProfileActivity: BaseActivity<ProfileViewModel>() {

    override val viewModelClass: Class<ProfileViewModel> get() = ProfileViewModel::class.java
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, android.R.color.white)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
        }else{
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
            window.statusBarColor = Color.WHITE
        }

        intent.getStringExtra("DASHBOARD_DATA")?.let {
            viewModel.dashboardData = Gson().fromJson(it, DashboardBalance::class.java)

            binding.balance.text = viewModel.dashboardData.sisaSaldo
            binding.balanceIn.text = viewModel.dashboardData.dATAIN
            binding.balanceOut.text = viewModel.dashboardData.dATAOUT

            if(viewModel.dashboardData.iDPendanaanDisetujui != "0"){
                //has credit approved
                //binding.viewCreditIdentity.visibility = View.VISIBLE
                binding.viewCreditReqPrompt.visibility = View.GONE
                binding.viewCreditInfo.visibility = View.VISIBLE
                binding.valueActiveCredit.text = viewModel.dashboardData.pinjamanAktif
                binding.valueLimitCredit.text = viewModel.dashboardData.limitPinjaman
                binding.btnDetailCredit.setOnClickListener {
                    startActivity(
                        Intent(this@ProfileActivity, CreditDetailActivity::class.java)
                            .putExtra("BALANCE", viewModel.dashboardData.pinjamanAktif)
                            .putExtra("LIMIT", viewModel.dashboardData.limitPinjaman)
                    )
                }
            }
        }

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
                    Intent(this@ProfileActivity, SplashcreenActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                )
                this@ProfileActivity.finish()
                d.dismiss()
            }
            logoutDialog.show()
        }

        binding.actionTerms.setOnClickListener {
            viewModel.loadTermsConditions()
        }

        binding.actionFaq.setOnClickListener {
            startActivity(
                Intent(this, FaqActivity::class.java)
            )
        }

        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadProfile()
    }

    private fun observeViewModel(){
        viewModel.onTermsConditionsLoaded.observe(this){
            startActivity(
                Intent(this, TermConditionActivity::class.java)
                    .putExtra("DATA", it.terms)
            )
        }

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