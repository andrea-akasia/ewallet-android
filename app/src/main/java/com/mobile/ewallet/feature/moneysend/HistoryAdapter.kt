package com.mobile.ewallet.feature.moneysend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mobile.ewallet.databinding.ViewItemHistoryAccountBinding
import com.mobile.ewallet.model.api.sendmoney.HistoryTransferTransaction
import com.mobile.ewallet.util.GlideApp

class HistoryAdapter(
    val data: MutableList<HistoryTransferTransaction>
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
            GlideApp.with(holder.itemView.context)
                .load(item.photo)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.image)
            binding.name.text = item.nama
            binding.phone.text = item.phone
        }
    }

    override fun getItemCount(): Int = data.size
}