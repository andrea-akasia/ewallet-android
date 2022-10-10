package com.mobile.ewallet.feature.credit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.ewallet.databinding.ViewItemKodeposBinding
import com.mobile.ewallet.model.api.credit.KodePos

class KodePosAdapter(
    var data: MutableList<KodePos>
): RecyclerView.Adapter<KodePosAdapter.KodePosHolder>() {

    var listener: KodePosListener? = null

    fun update(newData: MutableList<KodePos>){
        data.clear()
        data.addAll(newData)
        this.notifyDataSetChanged()
    }

    class KodePosHolder(val binding: ViewItemKodeposBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KodePosHolder =
        KodePosHolder(
            ViewItemKodeposBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: KodePosHolder, position: Int) {
        with(holder){
            val item = data[bindingAdapterPosition]
            binding.value.text = item.description

            holder.itemView.setOnClickListener {
                listener?.onSelected(item)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    interface KodePosListener {
        fun onSelected(data: KodePos)
    }
}