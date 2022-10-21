package com.mobile.ewallet.feature.credit.billing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.ewallet.databinding.ViewItemBillingHistoryBinding
import com.mobile.ewallet.model.api.credit.billing.BillingTransaction

class BillingHistoryAdapter(
    val data: MutableList<BillingTransaction>
): RecyclerView.Adapter<BillingHistoryAdapter.TransactionHolder>() {

    class TransactionHolder(val binding: ViewItemBillingHistoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder =
        TransactionHolder(
            ViewItemBillingHistoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        with(holder){
            val item = data[bindingAdapterPosition]

            binding.balance.text = item.trxAmount
            binding.timestamp.text = item.timestamp
            binding.description.text = "${item.namaBank} Virtual Account"
        }
    }

    override fun getItemCount(): Int = data.size
}