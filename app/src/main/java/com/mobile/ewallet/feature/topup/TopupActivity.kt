package com.mobile.ewallet.feature.topup

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
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
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityTopupBinding
import com.mobile.ewallet.feature.credit.kum.KUMPrescreeningActivity
import com.mobile.ewallet.feature.credit.kur.KURPrecreeningActivity
import com.mobile.ewallet.model.api.topup.TopupInstruction
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
                binding.btnApplyCredit.setOnClickListener {
                    showSelectPengajuanTypeDialog()
                }
            }
        }

        observeViewModel()
        viewModel.loadVirtualAcc()
    }

    private fun observeViewModel() {
        viewModel.onTopupInstructionsLoaded.observe(this){
            showVirtualAccountInfo(viewModel.selectedVA!!, it)
        }

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

    private fun showVirtualAccountInfo(data: TopupVA, instructions: MutableList<TopupInstruction>){
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

    override fun onVASelected(data: TopupVA) {
        viewModel.selectedVA = data
        viewModel.loadTopupInstructions(data.iDBank)
        //showVirtualAccountInfo(data)
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