package com.mobile.ewallet.feature.topup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityTopupBinding

class TopupActivity: BaseActivity<TopupViewModel>() {

    override val viewModelClass: Class<TopupViewModel> get() = TopupViewModel::class.java
    private lateinit var binding: ActivityTopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.topbar.title.text = "Topup Saldo"

        intent.getBooleanExtra("IS_HAS_CREDIT_APPROVED", false).let {
            if(it){
                binding.viewCreditReqPrompt.visibility = View.GONE
            }else{
                binding.viewCreditReqPrompt.visibility = View.VISIBLE
            }
        }

        observeViewModel()
        viewModel.loadVirtualAcc()
    }

    private fun observeViewModel() {
        viewModel.onVirtualAccountLoaded.observe(this){
            if(it.isNotEmpty()){
                val vaAdapter = VirtualAccAdapter(it)
                binding.rv.layoutManager = LinearLayoutManager(this@TopupActivity)
                binding.rv.adapter = vaAdapter
            }else{
                Toast.makeText(this, "virtual account data is empty", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}