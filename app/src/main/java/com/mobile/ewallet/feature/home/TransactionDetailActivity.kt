package com.mobile.ewallet.feature.home

import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityDetailTransactionBinding
import com.mobile.ewallet.util.GlideApp
import com.mobile.ewallet.util.formatToCurrency

class TransactionDetailActivity: BaseActivity<HomeViewModel>() {

    override val viewModelClass: Class<HomeViewModel> get() = HomeViewModel::class.java
    private lateinit var binding: ActivityDetailTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.topbar.title.text = "Detail Transaksi"

        observeViewModel()

        intent.getStringExtra("ID")?.let {
            viewModel.loadTransactionDetail(it)
        }
    }

    private fun observeViewModel(){
        viewModel.onTransactionDetailLoaded.observe(this){
            GlideApp.with(this)
                .load(it.destinationPhoto)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.user_placeholder)
                .into(binding.image)
            binding.transactionId.text = "ID Transaksi ${it.idTransaction}"
            binding.name.text = it.name
            binding.phone.text = it.phone
            binding.type.text = it.transactionType
            binding.method.text = it.metodeBayar
            binding.reff.text = it.reffNumber
            binding.time.text = it.time
            binding.amount.text = it.amount.formatToCurrency()
            binding.admin.text = if(it.adminFeeText == "0") "Gratis" else it.adminFeeText.formatToCurrency()
            binding.total.text = it.total.formatToCurrency()
        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, "it", Toast.LENGTH_SHORT).show()
        }
    }
}