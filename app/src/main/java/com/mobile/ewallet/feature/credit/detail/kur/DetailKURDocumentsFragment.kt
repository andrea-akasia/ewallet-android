package com.mobile.ewallet.feature.credit.detail.kur

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import com.mindev.mindev_pdfviewer.MindevPDFViewer
import com.mobile.ewallet.base.BaseFragment
import com.mobile.ewallet.databinding.FragmentDetailKurDocumentsBinding
import com.mobile.ewallet.feature.credit.detail.PreviewPDFActivity
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
    }

    private fun observeViewModel() {
        viewModel.onDocumentLoaded.observe(viewLifecycleOwner) {
            GlideApp.with(this).load(it.photoKTP).into(_binding!!.imgKtp)
            GlideApp.with(this).load(it.photoKK).into(_binding!!.imgKk)

            if(it.suratPengajuan.contains("nopic", true)){
                _binding!!.actionUploadSuratPengajuan.visibility = View.GONE
            }else{
                _binding!!.pdfView.initializePDFDownloader(it.suratPengajuan, object: MindevPDFViewer.MindevViewerStatusListener {
                    override fun onFail(error: Throwable) {
                        Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
                    }
                    override fun onPageChanged(position: Int, total: Int) {}
                    override fun onProgressDownload(currentStatus: Int) {}
                    override fun onStartDownload() {}
                    override fun onSuccessDownLoad(path: String) {
                        _binding!!.pdfView.fileInit(path)
                        _binding!!.actionUploadSuratPengajuan.setOnClickListener { _ ->
                            startActivity(
                                Intent(activity, PreviewPDFActivity::class.java)
                                    .putExtra("URL", it.suratPengajuan)
                            )
                        }
                    }
                    override fun unsupportedDevice() {
                        Toast.makeText(activity, "unsupported device", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            if(it.photoKTP.contains("nopic", true)){
                _binding!!.imgKtp.visibility = View.GONE
            }
            if(it.photoKK.contains("nopic", true)){
                _binding!!.imgKk.visibility = View.GONE
            }
            if(!it.suratKuasa.contains("nopic", true)){
                _binding!!.pdfSuratKuasa.initializePDFDownloader(it.suratKuasa, object: MindevPDFViewer.MindevViewerStatusListener {
                    override fun onFail(error: Throwable) {
                        Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
                    }
                    override fun onPageChanged(position: Int, total: Int) {}
                    override fun onProgressDownload(currentStatus: Int) {}
                    override fun onStartDownload() {}
                    override fun onSuccessDownLoad(path: String) {
                        _binding!!.pdfSuratKuasa.fileInit(path)
                        _binding!!.actionUploadSuratKuasa.setOnClickListener { _ ->
                            startActivity(
                                Intent(activity, PreviewPDFActivity::class.java)
                                    .putExtra("URL", it.suratKuasa)
                            )
                        }
                    }
                    override fun unsupportedDevice() {
                        Toast.makeText(activity, "unsupported device", Toast.LENGTH_SHORT).show()
                    }
                })
            }else{
                _binding!!.actionUploadSuratKuasa.visibility = View.GONE
            }
            if(!it.checklistVerifikasi.contains(other = "nopic", ignoreCase = true)){
                _binding!!.pdfChecklist.initializePDFDownloader(it.checklistVerifikasi, object: MindevPDFViewer.MindevViewerStatusListener {
                    override fun onFail(error: Throwable) {
                        Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
                    }
                    override fun onPageChanged(position: Int, total: Int) {}
                    override fun onProgressDownload(currentStatus: Int) {}
                    override fun onStartDownload() {}
                    override fun onSuccessDownLoad(path: String) {
                        _binding!!.pdfChecklist.fileInit(path)
                        _binding!!.actionUploadChecklist.setOnClickListener { _ ->
                            startActivity(
                                Intent(activity, PreviewPDFActivity::class.java)
                                    .putExtra("URL", it.checklistVerifikasi)
                            )
                        }
                    }
                    override fun unsupportedDevice() {
                        Toast.makeText(activity, "unsupported device", Toast.LENGTH_SHORT).show()
                    }
                })
            }else{
                _binding!!.actionUploadChecklist.visibility = View.GONE
            }
            if(it.fileSIUPP.contains("nopic", true)){
                _binding!!.pdfSiup.initializePDFDownloader(it.fileSIUPP, object: MindevPDFViewer.MindevViewerStatusListener {
                    override fun onFail(error: Throwable) {
                        Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
                    }
                    override fun onPageChanged(position: Int, total: Int) {}
                    override fun onProgressDownload(currentStatus: Int) {}
                    override fun onStartDownload() {}
                    override fun onSuccessDownLoad(path: String) {
                        _binding!!.pdfSiup.fileInit(path)
                        _binding!!.actionUploadSiup.setOnClickListener { _ ->
                            startActivity(
                                Intent(activity, PreviewPDFActivity::class.java)
                                    .putExtra("URL", it.fileSIUPP)
                            )
                        }
                    }
                    override fun unsupportedDevice() {
                        Toast.makeText(activity, "unsupported device", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        viewModel.warningMessage.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }
    }
}