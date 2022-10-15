package com.mobile.ewallet.feature.topup

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.ewallet.databinding.ViewItemTopupVaInstructionBinding
import com.mobile.ewallet.model.api.topup.TopupInstruction

class InstructionAdapter(val data: MutableList<TopupInstruction>): RecyclerView.Adapter<InstructionAdapter.VAInstructionHolder>() {

    class VAInstructionHolder(val binding: ViewItemTopupVaInstructionBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VAInstructionHolder =
        VAInstructionHolder(
            ViewItemTopupVaInstructionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: VAInstructionHolder, position: Int) {
        with(holder){
            val item = data[bindingAdapterPosition]
            binding.method.text = item.method
            binding.instructions.text = Html.fromHtml(item.instructions, Html.FROM_HTML_MODE_COMPACT)

            binding.actionToggle.setOnClickListener {
                if(binding.instructions.visibility == View.GONE) {
                    binding.instructions.visibility = View.VISIBLE
                }else{
                    binding.instructions.visibility = View.GONE
                }
            }
        }
    }

    override fun getItemCount(): Int = data.size
}