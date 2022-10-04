package com.mobile.ewallet.feature.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mobile.ewallet.R
import com.mobile.ewallet.databinding.ViewItemBadgeBinding
import com.mobile.ewallet.model.api.badge.Badge
import com.mobile.ewallet.util.GlideApp

class BadgeAdapter(
    val data: MutableList<Badge>
): RecyclerView.Adapter<BadgeAdapter.BadgeHolder>() {

    class BadgeHolder(val binding: ViewItemBadgeBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BadgeHolder =
        BadgeHolder(
            ViewItemBadgeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: BadgeHolder, position: Int) {
        with(holder){
            val item = data[bindingAdapterPosition]
            GlideApp.with(holder.itemView.context)
                .load(item.badgeImage)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.imgBadge)

            binding.name.text = item.badge
            binding.title.text = item.badge
            binding.description.text = item.description
        }
    }

    override fun getItemCount(): Int = data.size
}