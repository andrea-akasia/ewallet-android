package com.mobile.ewallet.feature.auth

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.mobile.ewallet.R

class OTPDialog: DialogFragment() {

    fun newInstance(phone: String): OTPDialog {
        val frag = OTPDialog()
        val args = Bundle()
        args.putString("phone", phone)
        frag.arguments = args
        return frag
    }

    private lateinit var tvTimer: TextView
    private lateinit var actionResend: TextView
    var listener: OTPListener? = null
    var countdownTimer = object: CountDownTimer(30000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            tvTimer.text = "00:${formatSecond((millisUntilFinished/1000).toString())}"
        }

        override fun onFinish() {
            actionResend.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
            actionResend.setOnClickListener {
                start()
                actionResend.setTextColor(Color.parseColor("#7A7A7A"))
                listener?.onResendTrigger()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        countdownTimer.cancel()
        super.onDismiss(dialog)
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

            tvTimer = view.findViewById(R.id.value_timer)
            actionResend = view.findViewById(R.id.action_resend)
            actionResend.setTextColor(Color.parseColor("#7A7A7A"))
            countdownTimer.start()


            view.findViewById<TextView>(R.id.value_phone).text = requireArguments().getString("phone", "")
        }

        view.findViewById<EditText>(R.id.et_otp).addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                s?.let {
                    if(it.length == 5){
                        listener?.onInputComplete(it.toString())
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {}

        })

        return view
    }

    private fun formatSecond(second: String): String{
        return if(second.length > 1){
            second
        }else{
            "0$second"
        }
    }

    interface OTPListener {
        fun onInputComplete(otp: String)
        fun onResendTrigger()
    }

}