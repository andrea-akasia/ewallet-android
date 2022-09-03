package com.mobile.ewallet.feature.pay

import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityPayInputBinding

class PayInputActivity: BaseActivity<PayViewModel>() {

    override val viewModelClass: Class<PayViewModel> get() = PayViewModel::class.java
    private lateinit var binding: ActivityPayInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressed() }
        binding.topbar.title.text = "Detail Pembayaran"

        binding.btnSubmit.setOnClickListener {
            val dialog = BottomSheetDialog(this)

            val viewDialog = layoutInflater.inflate(R.layout.dialog_pay_confirmation, null)
            viewDialog.findViewById<TextView>(R.id.action_cancel).setOnClickListener {
                dialog.dismiss()
            }
            dialog.setContentView(viewDialog)

            dialog.show()
        }
    }
}