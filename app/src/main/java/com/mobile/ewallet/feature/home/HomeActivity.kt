package com.mobile.ewallet.feature.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.WindowInsetsController
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityHomeBinding
import com.mobile.ewallet.feature.auth.AuthActivity
import com.mobile.ewallet.feature.credit.CreditDetailActivity
import com.mobile.ewallet.feature.credit.kum.KUMPrescreeningActivity
import com.mobile.ewallet.feature.credit.kur.KURPrecreeningActivity
import com.mobile.ewallet.feature.moneyreq.MoneyRequestActivity
import com.mobile.ewallet.feature.moneysend.MoneySendTypeActivity
import com.mobile.ewallet.feature.profile.ProfileActivity
import com.mobile.ewallet.feature.scantosendmoney.ScannerActivity
import com.mobile.ewallet.feature.topup.TopupActivity
import com.mobile.ewallet.util.Constant.Companion.RC_PERMISSIONS
import com.mobile.ewallet.util.GlideApp

class HomeActivity: BaseActivity<HomeViewModel>() {

    override val viewModelClass: Class<HomeViewModel> get() = HomeViewModel::class.java
    private lateinit var binding: ActivityHomeBinding

    private var adapter: TransactionAdapter? = null
    private var isBalanceHidden: Boolean = true

    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA
    )

    private fun allPermissionsGranted(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(
                    this, permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RC_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Izin penggunakan kamera harus diberikan untuk dapat melanjutkan!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                startActivity(Intent(this@HomeActivity, ScannerActivity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
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

        binding.balanceTotal.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        binding.actionProfile.setOnClickListener {
            viewModel.balanceData?.let {
                startActivity(
                    Intent(this@HomeActivity, ProfileActivity::class.java)
                        .putExtra("DASHBOARD_DATA", Gson().toJson(it))
                )
            }
        }

        binding.viewBadge.setOnClickListener {
            startActivity(
                Intent(
                    this@HomeActivity,
                    BadgeActivity::class.java
                )
            )
        }

        binding.actionTopup.setOnClickListener {
            viewModel.balanceData?.let { balanceData ->
                startActivity(
                    Intent(this, TopupActivity::class.java)
                        .putExtra("IS_HAS_CREDIT_APPROVED", balanceData.iDPendanaanDisetujui != "0")
                )
            }
        }

        binding.actionScan.setOnClickListener {
            if (allPermissionsGranted()) {
                startActivity(Intent(this@HomeActivity, ScannerActivity::class.java))
            } else {
                ActivityCompat.requestPermissions(
                    this@HomeActivity, REQUIRED_PERMISSIONS, RC_PERMISSIONS
                )
            }
        }

        binding.actionMoneyReq.setOnClickListener {
            startActivity(
                Intent(this@HomeActivity, MoneyRequestActivity::class.java)
            )
        }

        binding.actionSend.setOnClickListener {
            startActivity(
                Intent(this@HomeActivity, MoneySendTypeActivity::class.java)
            )
        }

        binding.actionToggleBalanceVisibility.setOnClickListener {
            isBalanceHidden = !isBalanceHidden
            if(isBalanceHidden){
                binding.balanceTotal.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.actionToggleBalanceVisibility.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_balance_hide))
            }else{
                binding.balanceTotal.inputType = InputType.TYPE_CLASS_TEXT
                binding.actionToggleBalanceVisibility.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_balance_visible))
            }
        }

        binding.btnApplyCredit.setOnClickListener {
            showSelectPengajuanTypeDialog()
        }

        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        if(!viewModel.isLoggedIn()){
            startActivity(
                Intent(this@HomeActivity, AuthActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
            this@HomeActivity.finish()
        }else{
            viewModel.loadProfile()
            viewModel.loadDashboardBalance()
            viewModel.loadHistoryTransaction()
        }
    }

    private fun observeViewModel(){
        viewModel.onLatestCreditReqLoaded.observe(this){
            binding.viewCreditReqPrompt.visibility = View.GONE
            binding.viewCreditReqStatus.visibility = View.VISIBLE
            if(it.statusProses == "PENDING"){
                //in progress
                binding.tvInProgress.visibility = View.VISIBLE
                binding.tvDeclined.visibility = View.GONE
            }else{
                //ditolak
                binding.tvInProgress.visibility = View.GONE
                binding.tvDeclined.visibility = View.VISIBLE
            }

            binding.viewCreditReqStatus.setOnClickListener { v ->
                startActivity(
                    Intent(this, DetailStatusCreditReqActivity::class.java)
                        .putExtra("DATA", Gson().toJson(it/*.apply { statusProses = "DECLINED" }*/))
                )
            }
        }

        viewModel.onHistoryTransactionLoaded.observe(this){
            adapter = TransactionAdapter(it)
            binding.rv.layoutManager = LinearLayoutManager(this)
            binding.rv.adapter = adapter
        }

        viewModel.onDashboardBalanceLoaded.observe(this){
            GlideApp.with(this)
                .load(it.badgeImage)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.imgBadge)
            binding.balanceTotal.setText(it.sisaSaldo)
            binding.valueLastTopup.text = "+${it.lastTopup}"
            binding.statusBadge.text = it.badge
            binding.balanceIn.text = it.dATAIN
            binding.balanceOut.text = it.dATAOUT

            it.iDPendanaanDisetujui?.let { approvedPendanaanId ->
                if(approvedPendanaanId != "0"){
                    //show credit info
                    binding.viewCreditReqPrompt.visibility = View.GONE
                    binding.viewCreditInfo.visibility = View.VISIBLE
                    binding.valueLimitCredit.text = it.limitPinjaman
                    binding.valueActiveCredit.text = it.pinjamanAktif

                    binding.btnDetailCredit.setOnClickListener { _ ->
                        startActivity(
                            Intent(this@HomeActivity, CreditDetailActivity::class.java)
                                .putExtra("BALANCE", it.pinjamanAktif)
                                .putExtra("LIMIT", it.limitPinjaman)
                        )
                    }
                }else{
                    binding.btnDetailCredit.setOnClickListener(null)
                    binding.viewCreditInfo.visibility = View.GONE
                    binding.viewCreditReqPrompt.visibility = View.VISIBLE
                    viewModel.loadLatestPendanaanReqStatus()
                }
            }
        }

        viewModel.onProfileLoaded.observe(this){
            Glide.with(this)
                .load(it.photoProfileThumbnail)
                .placeholder(R.drawable.user_placeholder)
                .into(binding.userImage)
        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showSelectPengajuanTypeDialog(){
        val dialog = BottomSheetDialog(this)
        val viewDialog = layoutInflater.inflate(R.layout.dialog_select_pengajuan_type, null)


        viewDialog.findViewById<TextView>(R.id.action_close).setOnClickListener {
            dialog.dismiss()
        }
        viewDialog.findViewById<TextView>(R.id.btn_kum).setOnClickListener {
            startActivity(
                Intent(this, KUMPrescreeningActivity::class.java)
            )
            dialog.dismiss()
        }
        viewDialog.findViewById<TextView>(R.id.btn_kur).setOnClickListener {
            startActivity(
                Intent(this, KURPrecreeningActivity::class.java)
            )
            dialog.dismiss()
        }
        dialog.setContentView(viewDialog)
        dialog.show()
    }
}