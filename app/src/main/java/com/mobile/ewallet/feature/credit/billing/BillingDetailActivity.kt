package com.mobile.ewallet.feature.credit.billing

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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityBillingDetailBinding
import com.mobile.ewallet.feature.topup.InstructionAdapter
import com.mobile.ewallet.model.api.credit.billing.BillingCredit
import com.mobile.ewallet.model.api.credit.billing.BillingVA
import com.mobile.ewallet.model.api.topup.TopupInstruction
import com.mobile.ewallet.util.GlideApp

class BillingDetailActivity: BaseActivity<BillingViewModel>(), BillingVAAdapter.BillingVAListener {

    override val viewModelClass: Class<BillingViewModel> get() = BillingViewModel::class.java
    private lateinit var binding: ActivityBillingDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBillingDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.topbar.title.text = "Bayar Tagihan Pinjaman"


        observeViewModel()
        intent.getStringExtra("DATA")?.let {
            viewModel.billingData = Gson().fromJson(it, BillingCredit::class.java)
            binding.totalInvoice.text = viewModel.billingData.totalTagihan
            binding.valueCreditActive.text = viewModel.billingData.pinjamanAktif
            binding.valueDeadline.text = viewModel.billingData.tanggalJatuhTempo
            binding.valueBunga.text = viewModel.billingData.bunga
            binding.valueDenda.text = viewModel.billingData.dendaKeterlambatan

            viewModel.loadBillingDetail(viewModel.billingData.iD)
        }

        viewModel.loadBillingHistory()
    }

    private fun observeViewModel(){
        viewModel.onTopupInstructionsLoaded.observe(this){
            showVirtualAccountInfo(viewModel.selectedVA!!, it)
        }

        viewModel.onBillingHistoryLoaded.observe(this){
            if(it.isNotEmpty()){
                val historyAdapter = BillingHistoryAdapter(it)
                binding.rvTrx.layoutManager = LinearLayoutManager(this@BillingDetailActivity)
                binding.rvTrx.adapter = historyAdapter
            }else{
                binding.viewEmptyTrx.visibility = View.VISIBLE
                binding.rvTrx.visibility = View.GONE
            }
        }

        viewModel.onBillingDetailLoaded.observe(this){
            if(it.isNotEmpty()){
                val vaAdapter = BillingVAAdapter(it, this@BillingDetailActivity)
                binding.rv.layoutManager = LinearLayoutManager(this@BillingDetailActivity)
                binding.rv.adapter = vaAdapter
            }else{
                Toast.makeText(this, "virtual account data is empty", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onVASelected(data: BillingVA) {
        viewModel.selectedVA = data
        viewModel.loadTopupInstructions(data.iDBank)
    }

    private fun showVirtualAccountInfo(data: BillingVA, instructions: MutableList<TopupInstruction>){
        val dialog = BottomSheetDialog(this)
        val viewDialog = layoutInflater.inflate(R.layout.dialog_va, null)

        val instructionAdapter = InstructionAdapter(instructions)
        viewDialog.findViewById<RecyclerView>(R.id.rv).layoutManager = LinearLayoutManager(this)
        viewDialog.findViewById<RecyclerView>(R.id.rv).adapter = instructionAdapter

        GlideApp.with(this)
            .load(data.logo)
            .placeholder(R.drawable.img_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
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

    private fun copyToClipboard(data: String){
        val clipboard: ClipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("va", data)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Kode Virtual Account Telah Disalin", Toast.LENGTH_SHORT).show()
    }
}