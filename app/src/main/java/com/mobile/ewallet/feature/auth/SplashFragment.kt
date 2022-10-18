package com.mobile.ewallet.feature.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mobile.ewallet.databinding.FragmentSplashcreenBinding
import com.mobile.ewallet.util.GlideApp

class SplashFragment: Fragment() {

    private lateinit var binding: FragmentSplashcreenBinding

    companion object {
        const val KEY_TITLE = "TITLE"
        const val KEY_SUBTITLE = "SUBTITLE"
        const val KEY_IMAGE = "IMAGE"
        fun newInstance(title: String, subtitle: String, imageUrl: String) = SplashFragment().apply {
            arguments = bundleOf(
                KEY_TITLE to title,
                KEY_SUBTITLE to subtitle,
                KEY_IMAGE to imageUrl
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashcreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(KEY_IMAGE)?.let {
            GlideApp.with(requireActivity())
                .load(it)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.imageView)
        }
        arguments?.getString(KEY_TITLE)?.let { binding.title.text = it }
        arguments?.getString(KEY_SUBTITLE)?.let { binding.subtitle.text = it }
    }

}