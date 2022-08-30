package com.mobile.ewallet.feature.auth

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.mobile.ewallet.R

class OTPDialog: DialogFragment() {

    fun newInstance(): OTPDialog {
        return OTPDialog()
    }

    override fun onResume() {
        //dialog?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        super.onResume()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.dialog_otp, container)
        dialog?.let { dialog ->
            dialog.window?.let { window ->
                //window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window.requestFeature(Window.FEATURE_NO_TITLE)
            }
        }

        return view
    }

}