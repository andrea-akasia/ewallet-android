package com.mobile.ewallet.feature.credit

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.ewallet.R
import com.mobile.ewallet.model.api.credit.LokasiDatill

class DatillSearchDialog: DialogFragment(), DatillAdapter.DatillListener {

    private var data = mutableListOf<LokasiDatill>()
    lateinit var datillAdapter: DatillAdapter

    fun newInstance(): DatillSearchDialog {
        return DatillSearchDialog()
    }

    fun updateData(data: MutableList<LokasiDatill>){
        datillAdapter.update(data)
    }

    var listener: SearchDatillListener? = null

    override fun onResume() {
        val height = (resources.displayMetrics.heightPixels * 0.70).toInt()
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        dialog?.window?.setLayout(width, height)
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.dialog_search_datill, container)
        dialog?.let { dialog ->
            dialog.window?.let { window ->
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window.requestFeature(Window.FEATURE_NO_TITLE)
            }

            val rv = view.findViewById<RecyclerView>(R.id.rv)
            datillAdapter = DatillAdapter(data)
            datillAdapter.listener = this@DatillSearchDialog
            rv.layoutManager = LinearLayoutManager(requireContext())
            rv.adapter = datillAdapter

            val searchText = view.findViewById<EditText>(R.id.et_search)
            searchText.setOnEditorActionListener { _, actionId, _ ->
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    listener?.onDatillKeywordSubmited(searchText.text.toString())
                    true
                }else{
                    false
                }
            }
        }

        return view
    }

    interface SearchDatillListener{
        fun onDatillKeywordSubmited(keyword: String)
        fun onDatillSelected(data: LokasiDatill)
    }

    override fun onDatillSelected(data: LokasiDatill) {
        listener?.onDatillSelected(data)
    }

}