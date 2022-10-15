package com.mobile.ewallet.feature.credit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.ewallet.databinding.ViewItemKodeposBinding
import com.mobile.ewallet.model.api.credit.LokasiDatill

class DatillAdapter(
    var data: MutableList<LokasiDatill>
): RecyclerView.Adapter<DatillAdapter.DatillHolder>() {

    var listener: DatillListener? = null

    fun update(newData: MutableList<LokasiDatill>){
        data.clear()
        data.addAll(newData)
        this.notifyDataSetChanged()
    }

    class DatillHolder(val binding: ViewItemKodeposBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatillHolder =
        DatillHolder(
            ViewItemKodeposBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: DatillHolder, position: Int) {
        with(holder){
            val item = data[bindingAdapterPosition]
            binding.value.text = item.description

            holder.itemView.setOnClickListener {
                listener?.onDatillSelected(item)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    interface DatillListener {
        fun onDatillSelected(data: LokasiDatill)
    }
}