package com.mobile.ewallet.feature.pay

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityPayInputBinding
import com.mobile.ewallet.model.api.sendmoney.banktransfer.MinimumNominalTrfResponse
import com.mobile.ewallet.model.api.sendmoney.bycontact.ContactUser
import com.mobile.ewallet.model.api.sendmoney.byscan.AdminFeeResponse
import com.mobile.ewallet.util.GlideApp
import com.mobile.ewallet.util.formatToCurrency

class PayInputActivity: BaseActivity<PayViewModel>() {

    override val viewModelClass: Class<PayViewModel> get() = PayViewModel::class.java
    private lateinit var binding: ActivityPayInputBinding

    lateinit var qr: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressed() }
        binding.topbar.title.text = "Detail Pembayaran"

        observeViewModel()
        intent.getStringExtra("ACTION")?.let { action ->
            if(action == "QR"){
                intent.getStringExtra("QR")?.let {
                    viewModel.action = "QR"
                    qr = it
                    viewModel.loadMinimumNominal(qr)
                }
            }else if(action == "BANK"){
                viewModel.action = "BANK"
                viewModel.transferBankMinimumNominalData = Gson().fromJson(intent.getStringExtra("DATA"), MinimumNominalTrfResponse::class.java)
                GlideApp.with(this)
                    .load(viewModel.transferBankMinimumNominalData.photo)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.user_placeholder)
                    .into(binding.image)
                binding.name.text = viewModel.transferBankMinimumNominalData.name
                binding.phone.text = "${viewModel.transferBankMinimumNominalData.norek} (${viewModel.transferBankMinimumNominalData.bank})"
                viewModel.minimumAmount = viewModel.transferBankMinimumNominalData.minimalPengiriman!!.toInt()
                binding.minimum.text = viewModel.transferBankMinimumNominalData.minimalPengiriman!!.formatToCurrency()
            } else if(action == "CONTACT"){
                viewModel.action == "CONTACT"
                intent.getStringExtra("DATA")?.let { contactString ->
                    val contactUser = Gson().fromJson(contactString, ContactUser::class.java)
                    viewModel.loadContactMinimumNominal(contactUser.iD)
                }
            } else{

            }
        }



        binding.btnSubmit.setOnClickListener {
            validateForm()
        }
    }

    private fun validateForm(){
        if(binding.etAmount.text.isEmpty()){
            Toast.makeText(this, "jumlah harus diisi", Toast.LENGTH_SHORT).show()
            return
        }
        if(binding.etAmount.text.toString().toInt() < viewModel.minimumAmount){
            Toast.makeText(
                this,
                "jumlah minimum adalah ${viewModel.minimumAmount}",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if(viewModel.action == "QR"){
            viewModel.loadAdminFee(qr, binding.etAmount.text.toString())
        }else if(viewModel.action == "BANK"){
            viewModel.loadAdminFeeTransfer(
                idBank = intent.getStringExtra("ID_BANK")!!,
                accountNumber = viewModel.transferBankMinimumNominalData.norek,
                amount = binding.etAmount.text.toString()
            )
        }else if(viewModel.action == "CONTACT"){

        }
    }

    private fun showConfirmationDialog(data: AdminFeeResponse){
        val dialog = BottomSheetDialog(this)
        val viewDialog = layoutInflater.inflate(R.layout.dialog_pay_confirmation, null)

        GlideApp.with(this)
            .load(data.destinationPhoto)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(viewDialog.findViewById(R.id.image))
        viewDialog.findViewById<TextView>(R.id.name).text = data.destinationName
        viewDialog.findViewById<TextView>(R.id.phone).text = data.destinationPhone
        viewDialog.findViewById<TextView>(R.id.amount).text = data.amount.formatToCurrency()
        viewDialog.findViewById<TextView>(R.id.admin).text = if(data.adminFee == "0") "Gratis" else data.adminFee.formatToCurrency()
        viewDialog.findViewById<TextView>(R.id.total).text = data.total.formatToCurrency()


        viewDialog.findViewById<TextView>(R.id.action_cancel).setOnClickListener {
            dialog.dismiss()
        }
        viewDialog.findViewById<TextView>(R.id.btn_confirm).setOnClickListener {
            if(viewModel.action == "QR"){
                viewModel.scanSendMoney(
                    qr = qr,
                    amount = binding.etAmount.text.toString(),
                    adminFee = viewModel.adminFee.toString(),
                    total = viewModel.total.toString()
                )
            }else if(viewModel.action == "BANK"){
                viewModel.transferSendMoney(
                    idBank = intent.getStringExtra("ID_BANK")!!,
                    accountNumber = viewModel.transferBankMinimumNominalData.norek,
                    name = viewModel.transferBankMinimumNominalData.name,
                    amount = binding.etAmount.text.toString(),
                    adminFee = viewModel.adminFee.toString(),
                    total = viewModel.total.toString()
                )
            }

        }
        dialog.setContentView(viewDialog)
        dialog.show()
    }

    private fun observeViewModel(){
        viewModel.onAdminFeeTransferLoaded.observe(this){
            showConfirmationDialog(
                AdminFeeResponse(
                    amount = binding.etAmount.text.toString(),
                    adminFee = viewModel.adminFee.toString(),
                    total = viewModel.total.toString(),
                ).apply {
                    destinationName = it.name
                    destinationPhoto = it.photo
                    destinationPhone = "${it.norek} (${it.bank})"
                }
            )
        }

        viewModel.onTransactionSuccess.observe(this){
            startActivity(
                Intent(this@PayInputActivity, PayResultActivity::class.java)
                    .putExtra("DATA", Gson().toJson(it))
                    .putExtra("ACTION", viewModel.action)
            )
        }

        viewModel.onAdminFeeLoaded.observe(this){
            showConfirmationDialog(it)
        }

        viewModel.onMinimumNominalLoaded.observe(this){
            GlideApp.with(this)
                .load(it.destinationPhoto)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.user_placeholder)
                .into(binding.image)
            binding.name.text = it.destinationName
            binding.minimum.text = it.minimalText
            binding.phone.text = it.destinationPhone
        }

        viewModel.isLoading.observe(this){
            if(it){
                binding.btnSubmit.isClickable = false
                binding.btnSubmit.background = ContextCompat.getDrawable(this, R.drawable.orange_button_inactive_bg)
            }else{
                binding.btnSubmit.isClickable = true
                binding.btnSubmit.background = ContextCompat.getDrawable(this, R.drawable.orange_button_bg)
            }
        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}