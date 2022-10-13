package com.mobile.ewallet.feature.topup

import android.content.Intent
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityTopupViaKreditResultBinding
import com.mobile.ewallet.feature.home.HomeActivity
import com.mobile.ewallet.model.api.topup.TopupViaKreditResultResponse

class TopupViaKreditResultActivity: BaseActivity<TopupViewModel>() {

    override val viewModelClass: Class<TopupViewModel> get() = TopupViewModel::class.java
    private lateinit var binding: ActivityTopupViaKreditResultBinding

    private var transactionId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopupViaKreditResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, android.R.color.white)
        window.insetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding.actionClose.performClick()
            }
        })

        binding.btnGotoHome.setOnClickListener { binding.actionClose.performClick() }

        binding.actionClose.setOnClickListener {
            startActivity(
                Intent(
                    this@TopupViaKreditResultActivity,
                    HomeActivity::class.java
                )
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
            this@TopupViaKreditResultActivity.finish()
        }

        intent.getStringExtra("DATA")?.let {
            val data = Gson().fromJson(it, TopupViaKreditResultResponse::class.java)
            binding.total.text = data.nominalKirim
            binding.time.text = data.waktu
            binding.type.text = data.typeTransaksi
            binding.method.text = data.metodeBayar
            binding.reff.text  =data.iDTransaksi
        }
    }

}