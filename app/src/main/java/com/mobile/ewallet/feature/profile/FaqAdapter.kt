package com.mobile.ewallet.feature.profile

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.ewallet.databinding.ViewItemFaqBinding
import com.mobile.ewallet.model.api.profile.Faq

class FaqAdapter(val data: MutableList<Faq>): RecyclerView.Adapter<FaqAdapter.FaqHolder>() {

    class FaqHolder(val binding: ViewItemFaqBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqHolder =
        FaqHolder(
            ViewItemFaqBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: FaqHolder, position: Int) {
        with(holder){
            val item = data[bindingAdapterPosition]
            binding.question.text = Html.fromHtml(item.question, Html.FROM_HTML_MODE_COMPACT)
            binding.answer.text = Html.fromHtml(item.answer, Html.FROM_HTML_MODE_COMPACT)
        }
    }

    override fun getItemCount(): Int = data.size
}