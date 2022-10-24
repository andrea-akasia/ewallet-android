package com.mobile.ewallet.feature.credit.detail.kum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobile.ewallet.base.BaseFragment
import com.mobile.ewallet.databinding.FragmentDetailKumPrescreeningBinding

class DetailKUMPrescreeningFragment: BaseFragment<DetailKUMViewModel>() {
    override val viewModelClass: Class<DetailKUMViewModel> = DetailKUMViewModel::class.java
    private var _binding: FragmentDetailKumPrescreeningBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailKumPrescreeningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.let { v ->

        }
    }

}