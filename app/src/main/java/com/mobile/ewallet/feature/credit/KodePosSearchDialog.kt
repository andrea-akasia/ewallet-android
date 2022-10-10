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
import com.mobile.ewallet.model.api.credit.KodePos

class KodePosSearchDialog: DialogFragment(),
    KodePosAdapter.KodePosListener {

    private var data = mutableListOf<KodePos>()
    lateinit var kodePosAdapter: KodePosAdapter

    fun newInstance(): KodePosSearchDialog {
        return  KodePosSearchDialog()
    }

    fun updateData(data: MutableList<KodePos>){
        kodePosAdapter.update(data)
    }

    var listener: SearchKodePosListener? = null

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
        val view = inflater.inflate(R.layout.dialog_search_kodepos, container)
        dialog?.let { dialog ->
            dialog.window?.let { window ->
                //window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window.requestFeature(Window.FEATURE_NO_TITLE)
            }

            val rv = view.findViewById<RecyclerView>(R.id.rv)
            kodePosAdapter = KodePosAdapter(data)
            kodePosAdapter.listener = this@KodePosSearchDialog
            rv.layoutManager = LinearLayoutManager(requireContext())
            rv.adapter = kodePosAdapter

            val searchText = view.findViewById<EditText>(R.id.et_search)
            searchText.setOnEditorActionListener { _, actionId, _ ->
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    listener?.onKodePosKeywordSubmited(searchText.text.toString())
                    true
                }else{
                    false
                }
            }
        }

        return view
    }

    interface SearchKodePosListener{
        fun onKodePosKeywordSubmited(keyword: String)
        fun onKodePosSelected(data: KodePos)
    }

    override fun onSelected(data: KodePos) {
        listener?.onKodePosSelected(data)
    }

}