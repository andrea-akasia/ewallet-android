package com.mobile.ewallet.feature.credit.detail.kur

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import com.mobile.ewallet.base.BaseFragment
import com.mobile.ewallet.databinding.FragmentDetailKurDocumentsBinding
import com.mobile.ewallet.util.GlideApp

class DetailKURDocumentsFragment: BaseFragment<DetailKURViewModel>() {
    override val viewModelClass: Class<DetailKURViewModel> = DetailKURViewModel::class.java
    private var _binding: FragmentDetailKurDocumentsBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(id: String, isShowNpwp: Boolean = true) = DetailKURDocumentsFragment().apply {
            arguments = bundleOf(
                "ID" to id,
                "SHOWNPWP" to isShowNpwp
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailKurDocumentsBinding.inflate(inflater, container, false)
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

                viewModel.loadDocument(idPendanaan)
            }
        }

        arguments?.getBoolean("SHOWNPWP")?.let {
            if(it){
                _binding!!.actionUploadNpwp.visibility = View.VISIBLE
            }else{
                _binding!!.actionUploadNpwp.visibility = View.GONE
            }
        }
    }

    private fun observeViewModel() {
        viewModel.onDocumentLoaded.observe(viewLifecycleOwner) {
            GlideApp.with(this).load(it.photoKTP).into(_binding!!.imgKtp)
            GlideApp.with(this).load(it.photoKK).into(_binding!!.imgKk)
            GlideApp.with(this).load(it.photoSelfie).into(_binding!!.imgSelfie)
            GlideApp.with(this).load(it.photoNPWP).into(_binding!!.imgNpwp)
            GlideApp.with(this).load(it.fileSIUPP).into(_binding!!.imgSiup)

            if(!it.suratPengajuan.contains("nopic", true)){
                _binding!!.statusSurat.visibility = View.VISIBLE
            }
            if(it.photoKTP.contains("nopic", true)){
                _binding!!.imgKtp.visibility = View.GONE
            }
            if(it.photoKK.contains("nopic", true)){
                _binding!!.imgKk.visibility = View.GONE
            }
            if(it.photoSelfie.contains("nopic", true)){
                _binding!!.imgSelfie.visibility = View.GONE
            }
            if(it.photoNPWP.contains("nopic", true)){
                _binding!!.imgNpwp.visibility = View.GONE
            }
            if(it.fileSIUPP.contains("nopic", true)){
                _binding!!.imgSiup.visibility = View.GONE
            }
        }

        viewModel.warningMessage.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }
    }
}