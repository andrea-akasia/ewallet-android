package com.mobile.ewallet.feature.topup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityTopupViaKreditBinding
import com.mobile.ewallet.model.api.topup.TopupViaKreditResultResponse
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

        binding.btnSubmit.setOnClickListener { validateForm() }

        observeViewModel()
        viewModel.loadTopupViaKreditStat()
    }

    private fun validateForm(){
        val inputed = binding.etAmount.text.toString()
        if(inputed.isEmpty()){
            Toast.makeText(this, "jumlah harus diisi", Toast.LENGTH_SHORT).show()
            return
        }
        if(inputed.toInt() == 0){
            Toast.makeText(this, "jumlah tidak boleh 0", Toast.LENGTH_SHORT).show()
            return
        }
        if(inputed.toInt() > viewModel.maksimum){
            Toast.makeText(this, "jumlah maksimal adalah ${(viewModel.maksimum).toString().formatToCurrency()}", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.submitTopupViaKredit(inputed)
    }

    private fun observeViewModel(){
        viewModel.onTopupSuccess.observe(this){
            startActivity(
                Intent(this, TopupViaKreditResultActivity::class.java)
                    .putExtra("DATA", Gson().toJson(it))
            )
        }

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