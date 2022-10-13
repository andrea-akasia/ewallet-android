package com.mobile.ewallet.feature.topup

import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityTopupViaKreditBinding
import com.mobile.ewallet.util.formatToCurrency

class TopupViaKreditActivity: BaseActivity<TopupViewModel>() {

    override val viewModelClass: Class<TopupViewModel> get() = TopupViewModel::class.java
    private lateinit var binding: ActivityTopupViaKreditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopupViaKreditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.topbar.title.text = "Kirim Kredit Pinjaman"

        observeViewModel()
        viewModel.loadTopupViaKreditStat()
    }

    private fun observeViewModel(){
        viewModel.onTopupViaKreditStatLoaded.observe(this){
            binding.tvSisaSaldo.text = it.sisaSaldoKredit.formatToCurrency()
            binding.maximum.text = it.maksimalNominal.formatToCurrency()
        }

        viewModel.isLoading.observe(this){
            if(it){
                binding.btnSubmit.isClickable = false
                binding.btnSubmit.background = ContextCompat.getDrawable(this@TopupViaKreditActivity, R.drawable.orange_button_inactive_bg)
            }else{
                binding.btnSubmit.isClickable = true
                binding.btnSubmit.background = ContextCompat.getDrawable(this@TopupViaKreditActivity, R.drawable.orange_button_bg)
            }
        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}