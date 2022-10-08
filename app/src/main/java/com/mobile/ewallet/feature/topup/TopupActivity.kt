package com.mobile.ewallet.feature.topup

import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityTopupBinding
import com.mobile.ewallet.model.api.topup.TopupVA
import com.mobile.ewallet.util.GlideApp


class TopupActivity: BaseActivity<TopupViewModel>(), VirtualAccAdapter.VAListener {

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
                val vaAdapter = VirtualAccAdapter(it, this@TopupActivity)
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

    private fun copyToClipboard(data: String){
        val clipboard: ClipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("va", data)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Kode Virtual Account Telah Disalin", Toast.LENGTH_SHORT).show()
    }

    private fun showVirtualAccountInfo(data: TopupVA){
        val dialog = BottomSheetDialog(this)
        val viewDialog = layoutInflater.inflate(R.layout.dialog_va, null)

        GlideApp.with(this)
            .load(data.logo)
            .placeholder(R.drawable.img_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .centerCrop()
            .into(viewDialog.findViewById(R.id.image))
        viewDialog.findViewById<TextView>(R.id.name).text = "Virtual Account ${data.namaBank}"
        viewDialog.findViewById<TextView>(R.id.description).text = data.keterangan
        viewDialog.findViewById<TextView>(R.id.va_number).text = data.nOVA.replace("....".toRegex(), "$0 ")
        viewDialog.findViewById<TextView>(R.id.account_name).text = data.namaVA

        viewDialog.findViewById<Button>(R.id.btn_copy).setOnClickListener {
            copyToClipboard(data.nOVA)
        }

        viewDialog.findViewById<TextView>(R.id.action_close).setOnClickListener {
            dialog.dismiss()
        }

        dialog.setContentView(viewDialog)
        dialog.show()
    }

    override fun onVASelected(data: TopupVA) {
        showVirtualAccountInfo(data)
    }
}