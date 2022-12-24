package com.mobile.ewallet.feature.credit.kur

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.atwa.filepicker.core.FilePicker
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mindev.mindev_pdfviewer.MindevPDFViewer
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityKurDocumentBinding
import com.mobile.ewallet.feature.credit.CreditTermsDialog
import com.mobile.ewallet.feature.home.HomeActivity
import com.mobile.ewallet.util.GlideApp
import com.mobile.ewallet.util.LoadingDialog
import com.mobile.ewallet.util.getFile

class KURUploadDocumentsActivity: BaseActivity<KURDocumentViewModel>(),
    CreditTermsDialog.CreditTermsListener {

    override val viewModelClass: Class<KURDocumentViewModel> get() = KURDocumentViewModel::class.java
    private lateinit var binding: ActivityKurDocumentBinding

    private val filePicker = FilePicker.getInstance(this)
    private var loadingView: LoadingDialog? = null

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            if(viewModel.TAG == "KTP"){
                viewModel.uploadKTP(getFile(this, it))
            }else if(viewModel.TAG == "KK"){
                viewModel.uploadKK(getFile(this, it))
            }else if(viewModel.TAG == "NPWP"){
                viewModel.uploadNPWP(getFile(this, it))
            }else if(viewModel.TAG == "SELFIE"){
                viewModel.uploadSelfie(getFile(this, it))
            }else if(viewModel.TAG == "SURAT"){
                viewModel.uploadSurat(getFile(this, it))
            }else if(viewModel.TAG == "SIUP"){
                viewModel.uploadSIUP(getFile(this, it))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKurDocumentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.topbar.title.text = "KUR Document Upload"

        intent.getStringExtra("ID_REQUEST")?.let { viewModel.creditRequestId = it }

        binding.actionUploadKtp.setOnClickListener {
            viewModel.TAG = "KTP"
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.actionUploadNpwp.setOnClickListener {
            viewModel.TAG = "NPWP"
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.actionUploadKk.setOnClickListener {
            viewModel.TAG = "KK"
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.actionUploadSelfie.setOnClickListener {
            viewModel.TAG = "SELFIE"
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.actionUploadSuratPengajuan.setOnClickListener {
            filePicker.pickPdf { result ->
                result?.second?.let { file -> viewModel.uploadSurat(file) }
            }
        }

        binding.actionUploadSiup.setOnClickListener {
            viewModel.TAG = "SIUP"
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.btnContinue.setOnClickListener {
            viewModel.loadTerms()
        }

        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadKURPReview(viewModel.creditRequestId)
    }

    private fun observeViewModel(){
        viewModel.onKURPreviewLoaded.observe(this){ data ->
            if(!data.photoKTP!!.contains("nopic", true)){
                viewModel.onKTPSuccess.postValue(true)
                GlideApp.with(this)
                    .load(data.photoKTP)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .fitCenter()
                    .into(binding.imgKtp)
            }
            if(!data.photoKK!!.contains("nopic", true)){
                viewModel.onKKSuccess.postValue(true)
                GlideApp.with(this)
                    .load(data.photoKK)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .fitCenter()
                    .into(binding.imgKk)
            }
            if(!data.photoSelfie!!.contains("nopic", true)){
                viewModel.onSelfieSuccess.postValue(true)
                GlideApp.with(this)
                    .load(data.photoSelfie)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .fitCenter()
                    .into(binding.imgSelfie)
            }
            if(binding.actionUploadNpwp.visibility == View.VISIBLE){
                if(!data.photoNPWP!!.contains("nopic", true)){
                    viewModel.onNPWPSuccess.postValue(true)
                    GlideApp.with(this)
                        .load(data.photoNPWP)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .fitCenter()
                        .into(binding.imgNpwp)
                }
            }
            if(!data.suratPengajuan!!.contains("nopic", true)){
                viewModel.onSuratSuccess.postValue(true)
                binding.pdfSurat.pdfRendererCore?.clear()
                binding.pdfSurat.initializePDFDownloader(data.suratPengajuan, object: MindevPDFViewer.MindevViewerStatusListener{
                    override fun onFail(error: Throwable) {
                        Toast.makeText(
                            this@KURUploadDocumentsActivity,
                            "load pdf failed: ${error.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    override fun onPageChanged(position: Int, total: Int) {}
                    override fun onProgressDownload(currentStatus: Int) {}
                    override fun onStartDownload() {}
                    override fun onSuccessDownLoad(path: String) {
                        if(binding.pdfSurat.pdfRendererCore == null){
                            binding.pdfSurat.fileInit(path)
                        }
                    }

                    override fun unsupportedDevice() {
                        Toast.makeText(
                            this@KURUploadDocumentsActivity,
                            "load pdf failed: device not supported",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
            }
            if(!data.photoSIUP!!.contains("nopic", true)){
                viewModel.onSIUPSuccess.postValue(true)
                GlideApp.with(this)
                    .load(data.photoSIUP)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .fitCenter()
                    .into(binding.imgSiup)
            }
        }

        viewModel.isLoading.observe(this){
            if(it){
                loadingView = LoadingDialog().newInstance()
                loadingView?.isCancelable = false
                loadingView?.show(supportFragmentManager, null)
            }else{
                loadingView?.dismiss()
            }
        }

        viewModel.onSubmitSuccess.observe(this){
            startActivity(
                Intent(this@KURUploadDocumentsActivity, HomeActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
            this@KURUploadDocumentsActivity.finish()
        }

        viewModel.onTermsLoaded.observe(this){
            val dialog = CreditTermsDialog().newInstance(it)
            dialog.listener = this@KURUploadDocumentsActivity
            dialog.show(supportFragmentManager, null)
        }

        viewModel.onKTPSuccess.observe(this){
            binding.imgKtp.visibility = View.VISIBLE
            binding.statusKtp.visibility = View.VISIBLE
            binding.actionUploadKtp.background = ContextCompat.getDrawable(this, R.drawable.green_border_bg)
        }

        viewModel.onKKSuccess.observe(this){
            binding.imgKk.visibility = View.VISIBLE
            binding.statusKk.visibility = View.VISIBLE
            binding.actionUploadKk.background = ContextCompat.getDrawable(this, R.drawable.green_border_bg)
        }

        viewModel.onNPWPSuccess.observe(this){
            binding.imgNpwp.visibility = View.VISIBLE
            binding.statusNpwp.visibility = View.VISIBLE
            binding.actionUploadNpwp.background = ContextCompat.getDrawable(this, R.drawable.green_border_bg)
        }

        viewModel.onSelfieSuccess.observe(this){
            binding.imgSelfie.visibility = View.VISIBLE
            binding.statusSelfie.visibility = View.VISIBLE
            binding.actionUploadSelfie.background = ContextCompat.getDrawable(this, R.drawable.green_border_bg)
        }

        viewModel.onSuratSuccess.observe(this){
            binding.pdfSurat.visibility = View.VISIBLE
            binding.statusSurat.visibility = View.VISIBLE
            binding.actionUploadSuratPengajuan.background = ContextCompat.getDrawable(this, R.drawable.green_border_bg)
        }

        viewModel.onSIUPSuccess.observe(this){
            binding.imgSiup.visibility = View.VISIBLE
            binding.statusSiup.visibility = View.VISIBLE
            binding.actionUploadSiup.background = ContextCompat.getDrawable(this, R.drawable.green_border_bg)
        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSubmit() {
        viewModel.submitFinal()
    }
}