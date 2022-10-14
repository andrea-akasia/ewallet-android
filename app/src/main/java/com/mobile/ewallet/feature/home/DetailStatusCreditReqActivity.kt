package com.mobile.ewallet.feature.home

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityDetailStatusCreditReqBinding
import com.mobile.ewallet.feature.credit.kum.KUMPrescreeningActivity
import com.mobile.ewallet.feature.credit.kur.KURPrecreeningActivity
import com.mobile.ewallet.model.api.credit.PendanaanItem

class DetailStatusCreditReqActivity: BaseActivity<HomeViewModel>() {

    override val viewModelClass: Class<HomeViewModel> get() = HomeViewModel::class.java
    private lateinit var binding: ActivityDetailStatusCreditReqBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStatusCreditReqBinding.inflate(layoutInflater)
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

        binding.topbar.title.text = "Pengajuan Limit Kredit"
        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        binding.btnReq.setOnClickListener { showSelectPengajuanTypeDialog() }

        intent.getStringExtra("DATA")?.let {
            val pendanaanData = Gson().fromJson(it, PendanaanItem::class.java)
            if(pendanaanData.statusProses == "PENDING"){
                binding.infoInprogress.visibility = View.VISIBLE
                binding.viewInprogress.visibility = View.VISIBLE
            }else{
                binding.infoDeclined.visibility = View.VISIBLE
                binding.viewDeclined.visibility = View.VISIBLE
                binding.btnReq.visibility = View.VISIBLE
            }
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