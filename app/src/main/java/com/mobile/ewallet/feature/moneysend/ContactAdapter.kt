package com.mobile.ewallet.feature.moneysend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mobile.ewallet.databinding.ViewItemContactBinding
import com.mobile.ewallet.model.api.sendmoney.bycontact.ContactUser
import com.mobile.ewallet.util.GlideApp

class ContactAdapter(
    val data: MutableList<ContactUser>
): RecyclerView.Adapter<ContactAdapter.ContactHolder>() {

    var listener: ContactListener? = null

    class ContactHolder(val binding: ViewItemContactBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder =
        ContactHolder(
            ViewItemContactBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        with(holder){
            val item = data[bindingAdapterPosition]
            GlideApp.with(holder.itemView.context)
                .load(item.photo)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.image)
            binding.name.text = item.nama
            binding.phone.text = item.phone

            holder.itemView.setOnClickListener {
                listener?.onContactSelected(item)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    interface ContactListener {
        fun onContactSelected(data: ContactUser)
    }
}