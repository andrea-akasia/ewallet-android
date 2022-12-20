package com.mobile.ewallet.feature.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.InputType
import android.view.View
import android.view.WindowInsetsController
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
import com.mobile.ewallet.feature.auth.SplashcreenActivity
import com.mobile.ewallet.feature.credit.CreditDetailActivity
import com.mobile.ewallet.feature.credit.detail.DetailPendanaanActivity
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

        binding.actionSeeAll.setOnClickListener {
            startActivity(
                Intent(this, AllHistoryTransactionActivity::class.java)
            )
        }

        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        if(!viewModel.isLoggedIn()){
            startActivity(
                Intent(this@HomeActivity, SplashcreenActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
            this@HomeActivity.finish()
        }else{
            viewModel.loadProfile()
            viewModel.loadVersioning()
        }
    }

    private fun gotoDetailPendanaan(type: String, id: String){
        if(type.isNotEmpty()){
            startActivity(
                Intent(this, DetailPendanaanActivity::class.java)
                    .putExtra("TYPE", type)
                    .putExtra("ID", id)
            )
        }
    }

    private fun observeViewModel(){
        viewModel.onVersioningLoaded.observe(this) {
            GlideApp.with(this)
                .load(it.logo)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .centerInside()
                .into(binding.logo)
            binding.appName.text = it.appsName
        }

        viewModel.onApplyCreditInfoLoaded.observe(this) {
            binding.applyCreditTitle.text = it.title
            binding.applyCreditSubtitle.text = Html.fromHtml(it.message, Html.FROM_HTML_MODE_COMPACT)
            binding.btnApplyCredit.text = it.subMessage
            if(it.mode == "0"){
                binding.viewCreditReqPrompt.visibility = View.GONE
            }else if(it.mode == "1A"){
                //user can req
                binding.viewCreditReqPrompt.visibility = View.VISIBLE
                binding.viewApplyCreditBg.background = ContextCompat.getDrawable(this, R.drawable.apply_credit_bg_orange)
                binding.btnApplyCredit.visibility = View.VISIBLE
                binding.btnApplyCredit.setTextColor(Color.parseColor("#FFFFFF"))
                binding.btnApplyCredit.isClickable = true
                binding.btnApplyCredit.background = ContextCompat.getDrawable(this, R.drawable.orange_button_bg)
                binding.actionCloseApplyCredit.visibility = View.GONE
                binding.btnApplyCredit.setOnClickListener {
                    showSelectPengajuanTypeDialog()
                }
                binding.actionCancelPendanaan.visibility = View.GONE
            }else if(it.mode == "1B"){
                //in progress
                binding.viewCreditReqPrompt.visibility = View.VISIBLE
                binding.viewApplyCreditBg.background = ContextCompat.getDrawable(this, R.drawable.apply_credit_bg_orange)
                binding.btnApplyCredit.visibility = View.VISIBLE
                binding.btnApplyCredit.setTextColor(Color.parseColor("#FFFFFF"))
                binding.btnApplyCredit.isClickable = true
                binding.btnApplyCredit.background = ContextCompat.getDrawable(this, R.drawable.orange_button_bg)
                binding.actionCloseApplyCredit.visibility = View.GONE
                binding.btnApplyCredit.setOnClickListener { _ ->
                    gotoDetailPendanaan(it.typePengajuan, it.iDPendanaan)
                }
                binding.actionCancelPendanaan.visibility = View.VISIBLE
                binding.actionCancelPendanaan.setOnClickListener { _ ->
                    val logoutDialog = AlertDialog.Builder(this)
                    logoutDialog.setMessage("Anda yakin ingin Membatalkan?")
                    logoutDialog.setCancelable(false)
                    logoutDialog.setNegativeButton("tutup") { d, _ ->
                        d.dismiss()
                    }
                    logoutDialog.setPositiveButton("Batalkan Pendanaan") { d, _ ->
                        viewModel.cancelPendanaan(it.iDPendanaan)
                        d.dismiss()
                    }
                    logoutDialog.show()
                }
            }else if(it.mode == "2"){
                //approved
                binding.viewCreditReqPrompt.visibility = View.VISIBLE
                binding.viewApplyCreditBg.background = ContextCompat.getDrawable(this, R.drawable.apply_credit_bg_blue)
                binding.btnApplyCredit.visibility = View.VISIBLE
                binding.btnApplyCredit.background = ContextCompat.getDrawable(this, R.drawable.white_button_bg)
                binding.btnApplyCredit.setTextColor(Color.parseColor("#2196f3"))
                binding.btnApplyCredit.setOnClickListener { _ ->
                    gotoDetailPendanaan(it.typePengajuan, it.iDPendanaan)
                }
                binding.actionCloseApplyCredit.visibility = View.GONE
                binding.actionCancelPendanaan.visibility = View.GONE
            }else if(it.mode == "3"){
                //rejected
                binding.viewCreditReqPrompt.visibility = View.VISIBLE
                binding.viewApplyCreditBg.background = ContextCompat.getDrawable(this, R.drawable.apply_credit_bg_red)
                binding.btnApplyCredit.visibility = View.GONE
                binding.btnApplyCredit.visibility = View.VISIBLE
                binding.btnApplyCredit.background = ContextCompat.getDrawable(this, R.drawable.white_button_bg)
                binding.btnApplyCredit.setTextColor(Color.parseColor("#BC2727"))
                binding.btnApplyCredit.setOnClickListener { _ ->
                    gotoDetailPendanaan(it.typePengajuan, it.iDPendanaan)
                }
                binding.actionCloseApplyCredit.visibility = View.GONE
                binding.actionCancelPendanaan.visibility = View.GONE
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