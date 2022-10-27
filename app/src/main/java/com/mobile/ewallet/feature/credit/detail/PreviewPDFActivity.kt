package com.mobile.ewallet.feature.credit.detail

import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.mindev.mindev_pdfviewer.MindevPDFViewer
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityPreviewPdfBinding
import com.mobile.ewallet.feature.credit.CreditViewModel

class PreviewPDFActivity: BaseActivity<CreditViewModel>() {

    override val viewModelClass: Class<CreditViewModel> get() = CreditViewModel::class.java
    private lateinit var binding: ActivityPreviewPdfBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.topbar.title.text = "Detail PDF"

        val url = intent.getStringExtra("URL")!!
        binding.pdfView.initializePDFDownloader(url, object: MindevPDFViewer.MindevViewerStatusListener{
            override fun onFail(error: Throwable) {
                Toast.makeText(this@PreviewPDFActivity, error.message, Toast.LENGTH_SHORT).show()
            }

            override fun onPageChanged(position: Int, total: Int) {
            }

            override fun onProgressDownload(currentStatus: Int) {
            }

            override fun onStartDownload() {
            }

            override fun onSuccessDownLoad(path: String) {
                binding.pdfView.fileInit(path)
            }

            override fun unsupportedDevice() {
                Toast.makeText(this@PreviewPDFActivity, "unsupported device", Toast.LENGTH_SHORT).show()
            }

        })
    }
}