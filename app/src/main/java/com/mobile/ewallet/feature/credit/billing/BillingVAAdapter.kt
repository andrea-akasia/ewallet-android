package com.mobile.ewallet.feature.credit.billing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mobile.ewallet.R
import com.mobile.ewallet.databinding.ViewItemVaBinding
import com.mobile.ewallet.model.api.credit.billing.BillingVA
import com.mobile.ewallet.util.GlideApp

class BillingVAAdapter(
    val data: MutableList<BillingVA>,
    val listener: BillingVAListener
): RecyclerView.Adapter<BillingVAAdapter.VAHolder>() {

    class VAHolder(val binding: ViewItemVaBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VAHolder =
        VAHolder(
            ViewItemVaBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: VAHolder, position: Int) {
        with(holder){
            val item = data[bindingAdapterPosition]
            GlideApp.with(holder.itemView.context)
                .load(item.logo)
                .placeholder(R.drawable.img_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.image)

            binding.name.text = "Virtual Account ${item.namaBank}"
            binding.description.text = item.keterangan

            holder.itemView.setOnClickListener {
                listener.onVASelected(item)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    interface BillingVAListener{
        fun onVASelected(data: BillingVA)
    }
}