package com.mobile.ewallet.feature.credit.detail.kur

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.mobile.ewallet.base.BaseFragment
import com.mobile.ewallet.databinding.FragmentDetailKurFulfillmentBinding

class DetailKURFulfillmentFragment: BaseFragment<DetailKURViewModel>() {
    override val viewModelClass: Class<DetailKURViewModel> = DetailKURViewModel::class.java
    private var _binding: FragmentDetailKurFulfillmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(id: String) = DetailKURFulfillmentFragment().apply {
            arguments = bundleOf(
                "ID" to id
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailKurFulfillmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("ID")?.let { idPendanaan ->
            _binding?.let { _ ->
                observeViewModel()


            }
        }
    }

    private fun observeViewModel() {

    }
}