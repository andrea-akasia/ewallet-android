package com.mobile.ewallet.feature.moneysend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.ewallet.databinding.ViewItemHistoryAccountBinding
import com.mobile.ewallet.model.api.AccountItem

class HistoryAdapter(
    val data: MutableList<AccountItem>
): RecyclerView.Adapter<HistoryAdapter.AccountHolder>() {

    class AccountHolder(val binding: ViewItemHistoryAccountBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountHolder =
        AccountHolder(
            ViewItemHistoryAccountBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: AccountHolder, position: Int) {
        with(holder){
            val item = data[bindingAdapterPosition]
            binding.name.text = item.name
        }
    }

    override fun getItemCount(): Int = data.size
}