package com.mobile.ewallet.feature.credit

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.mobile.ewallet.R

class CreditTermsDialog: DialogFragment() {

    fun newInstance(data: String): CreditTermsDialog {
        val df = CreditTermsDialog()
        val args = Bundle()
        args.putString("data", data)
        df.arguments = args
        return df
    }

    var listener: CreditTermsListener? = null

    override fun onResume() {
        val height = (resources.displayMetrics.heightPixels * 0.70).toInt()
        val width = (resources.displayMetrics.widthPixels * 0.80).toInt()
        dialog?.window?.setLayout(width, height)
        super.onResume()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.dialog_terms_credit, container)
        dialog?.let { dialog ->
            dialog.window?.let { window ->
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window.requestFeature(Window.FEATURE_NO_TITLE)
            }

            val datahtml = requireArguments().getString("data", "")
            view.findViewById<TextView>(R.id.terms).text = Html.fromHtml(datahtml, Html.FROM_HTML_MODE_COMPACT)
        }

        return view
    }

    interface CreditTermsListener{
        fun onSubmit()
    }

}