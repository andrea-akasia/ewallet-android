package com.mobile.ewallet.feature.auth

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.mobile.ewallet.R

class OTPDialog: DialogFragment() {

    fun newInstance(): OTPDialog {
        return OTPDialog()
    }

    var listener: OTPListener? = null

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

        view.findViewById<EditText>(R.id.et_otp).addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                s?.let {
                    if(it.length == 6){
                        listener?.onInputComplete(it.toString())
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {}

        })

        return view
    }

    interface OTPListener {
        fun onInputComplete(otp: String)
    }

}