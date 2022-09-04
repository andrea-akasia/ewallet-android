package com.mobile.ewallet.feature.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mobile.ewallet.R
import com.mobile.ewallet.databinding.ViewItemTransactionBinding
import com.mobile.ewallet.model.api.TransactionItem

class TransactionAdapter(
    val data: MutableList<TransactionItem>
): RecyclerView.Adapter<TransactionAdapter.TransactionHolder>() {

    class TransactionHolder(val binding: ViewItemTransactionBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder =
        TransactionHolder(
            ViewItemTransactionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        with(holder){
            val item = data[bindingAdapterPosition]
            if(item.type == "IN"){
                binding.balance.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.colorTrxIn))
                binding.iconTrx.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_arrow_down_green))
            }else if(item.type == "OUT"){
                binding.balance.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.colorTrxOut))
                binding.iconTrx.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_arrow_up_red))
            }else{
                binding.balance.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.colorTrxPending))
                binding.iconTrx.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_pause))
            }

            holder.itemView.setOnClickListener {
                holder.itemView.context.startActivity(
                    Intent(
                        holder.itemView.context,
                        TransactionDetailActivity::class.java
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int = data.size
}