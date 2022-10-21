package com.mobile.ewallet.feature.credit

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityCreditInfoBinding
import com.mobile.ewallet.feature.credit.billing.BillingDetailActivity
import com.mobile.ewallet.feature.home.TransactionAdapter
import com.mobile.ewallet.feature.topup.InstructionAdapter
import com.mobile.ewallet.feature.topup.TopupViaKreditActivity
import com.mobile.ewallet.model.api.credit.NominalIncreaseLimit
import com.mobile.ewallet.model.api.credit.billing.BillingVA
import com.mobile.ewallet.model.api.topup.TopupInstruction
import com.mobile.ewallet.util.GlideApp
import com.mobile.ewallet.util.removeCharExceptNumber
import org.w3c.dom.Text
import timber.log.Timber

class CreditDetailActivity: BaseActivity<CreditViewModel>() {

    override val viewModelClass: Class<CreditViewModel> get() = CreditViewModel::class.java
    private lateinit var binding: ActivityCreditInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreditInfoBinding.inflate(layoutInflater)
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

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.topbar.title.text = "Saldo Kredit"
        intent.getStringExtra("BALANCE")?.let {
            binding.balanceTotal.setText(it)
        }
        intent.getStringExtra("LIMIT")?.let {
            binding.valueLimit.text = it
        }

        binding.actionTopup.setOnClickListener {
            startActivity(
                Intent(this, TopupViaKreditActivity::class.java)
            )
        }

        binding.actionBilling.setOnClickListener {
            viewModel.billingData?.let { bc ->
                startActivity(
                    Intent(this, BillingDetailActivity::class.java)
                        .putExtra("DATA", Gson().toJson(bc))
                )
            }
        }

        binding.btnApplyIncreaseLimit.setOnClickListener {
            viewModel.increaseLimitInfo?.let {
                viewModel.loadNominalIncreaseLimit()
            }
        }
        binding.actionClose.setOnClickListener { binding.viewApplyLimitIncrease.visibility = View.GONE }

        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCreditHistoryTransaction()
        viewModel.loadBillingData()
        viewModel.loadIncreaseLimitInfo()
    }

    private fun observeViewModel(){
        viewModel.isLoading.observe(this) {
            if(it){
                binding.btnApplyIncreaseLimit.isClickable = false
                binding.btnApplyIncreaseLimit.background = ContextCompat.getDrawable(this, R.drawable.white_button_bg_inactive)
            }else{
                binding.btnApplyIncreaseLimit.isClickable = true
                binding.btnApplyIncreaseLimit.background = ContextCompat.getDrawable(this, R.drawable.white_button_bg)
            }
        }

        viewModel.onNominalIncreaseLimitLoaded.observe(this) {
            showIncreaseLimitFormDialog(viewModel.increaseLimitInfo!!.formIntro, it)
        }

        viewModel.onIncreaseLimitInfoLoaded.observe(this) {
            binding.increaseLimitTitle.text = it.title
            binding.increaseLimitSubtitle.text = it.message
            binding.btnApplyIncreaseLimit.text = it.subMessage
            binding.actionClose.text = it.subMessage
            if(it.mode == "0"){
                binding.viewApplyLimitIncrease.visibility = View.GONE
            }else if(it.mode == "1A"){
                //user can req
                binding.viewApplyLimitIncrease.visibility = View.VISIBLE
                binding.viewIncreaseLimitBg.background = ContextCompat.getDrawable(this, R.drawable.apply_increase_limit_bg_green)
                binding.btnApplyIncreaseLimit.visibility = View.VISIBLE
                binding.btnApplyIncreaseLimit.setTextColor(Color.parseColor("#41B92A"))
                binding.btnApplyIncreaseLimit.isClickable = true
                binding.btnApplyIncreaseLimit.background = ContextCompat.getDrawable(this, R.drawable.white_button_bg)
                binding.actionClose.visibility = View.GONE
            }else if(it.mode == "1B"){
                //in progress
                binding.viewApplyLimitIncrease.visibility = View.VISIBLE
                binding.viewIncreaseLimitBg.background = ContextCompat.getDrawable(this, R.drawable.apply_increase_limit_bg_green)
                binding.btnApplyIncreaseLimit.visibility = View.VISIBLE
                binding.btnApplyIncreaseLimit.setTextColor(Color.parseColor("#41B92A"))
                binding.btnApplyIncreaseLimit.isClickable = false
                binding.btnApplyIncreaseLimit.background = ContextCompat.getDrawable(this, R.drawable.white_button_bg_inactive)
                binding.actionClose.visibility = View.GONE
            }else if(it.mode == "2"){
                //approved
                binding.viewApplyLimitIncrease.visibility = View.VISIBLE
                binding.viewIncreaseLimitBg.background = ContextCompat.getDrawable(this, R.drawable.apply_increase_limit_bg_blue)
                binding.btnApplyIncreaseLimit.visibility = View.GONE
                binding.actionClose.visibility = View.VISIBLE
            }else if(it.mode == "3"){
                //rejected
                binding.viewApplyLimitIncrease.visibility = View.VISIBLE
                binding.viewIncreaseLimitBg.background = ContextCompat.getDrawable(this, R.drawable.apply_increase_limit_bg_red)
                binding.btnApplyIncreaseLimit.visibility = View.GONE
                binding.actionClose.visibility = View.VISIBLE
            }
        }

        viewModel.onIncreaseLimitSubmitted.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            onResume()
        }

        viewModel.onBillingAvailable.observe(this) {
            if(it){
                binding.viewBillingAvailable.visibility = View.VISIBLE
                binding.viewBillingUnavailable.visibility = View.GONE
            }else{
                binding.viewBillingAvailable.visibility = View.GONE
                binding.viewBillingUnavailable.visibility = View.VISIBLE
            }
        }

        viewModel.onCreditHistoryTransactionLoaded.observe(this){
            if(it.isNotEmpty()){
                binding.rv.visibility = View.VISIBLE
                binding.viewEmpty.visibility = View.GONE
                val trxAdapter = TransactionAdapter(it)
                binding.rv.layoutManager = LinearLayoutManager(this)
                binding.rv.adapter = trxAdapter
            }else{
                binding.rv.visibility = View.GONE
                binding.viewEmpty.visibility = View.VISIBLE
            }

        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showIncreaseLimitFormDialog(notes: String, nominals: MutableList<String>){
        val dialog = BottomSheetDialog(this)
        val viewDialog = layoutInflater.inflate(R.layout.dialog_apply_increase_limit, null)
        var selectedNominal = "0"

        viewDialog.findViewById<TextView>(R.id.notes).text = notes

        val spinnerAmount = viewDialog.findViewById<Spinner>(R.id.spinner_amount)
        ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, nominals).also { adptr ->
            adptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            spinnerAmount.adapter = adptr
            spinnerAmount.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    Timber.i("nominal selected: ${nominals[position]}")
                    selectedNominal = nominals[position].removeCharExceptNumber()
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        viewDialog.findViewById<Button>(R.id.btn_submit).setOnClickListener {
            viewModel.submitIncreaseLimit(selectedNominal)
            dialog.dismiss()
        }

        viewDialog.findViewById<TextView>(R.id.action_close).setOnClickListener {
            dialog.dismiss()
        }

        dialog.setContentView(viewDialog)
        dialog.show()
    }
}