package com.mobile.ewallet.feature.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mobile.ewallet.R
import com.mobile.ewallet.databinding.ViewItemTransactionBinding
import com.mobile.ewallet.model.api.dashboard.TransactionItem
import com.mobile.ewallet.util.GlideApp

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
            GlideApp.with(holder.itemView.context)
                .load(item.icon)
                .into(binding.icon)
            if(item.debitCredit == "Debit"){
                binding.balance.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.colorTrxIn))
            }else if(item.debitCredit == "Credit"){
                binding.balance.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.colorTrxOut))
            }else{
                binding.balance.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.colorTrxPending))
            }
            binding.balance.text = item.nominal
            binding.timestamp.text = item.timestamp
            binding.description.text = item.title

            /*holder.itemView.setOnClickListener {
                holder.itemView.context.startActivity(
                    Intent(
                        holder.itemView.context,
                        TransactionDetailActivity::class.java
                    )
                )
            }*/
        }
    }

    override fun getItemCount(): Int = data.size
}