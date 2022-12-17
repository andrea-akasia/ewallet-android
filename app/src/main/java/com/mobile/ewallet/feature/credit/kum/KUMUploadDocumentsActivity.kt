package com.mobile.ewallet.feature.credit.kum

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.atwa.filepicker.core.FilePicker
import com.bumptech.glide.Glide
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityKumDocumentBinding
import com.mobile.ewallet.feature.credit.CreditTermsDialog
import com.mobile.ewallet.feature.home.HomeActivity
import com.mobile.ewallet.util.getFile
import timber.log.Timber

class KUMUploadDocumentsActivity: BaseActivity<KUMDocumentViewModel>(),
    CreditTermsDialog.CreditTermsListener {

    override val viewModelClass: Class<KUMDocumentViewModel> get() = KUMDocumentViewModel::class.java
    private lateinit var binding: ActivityKumDocumentBinding

    private val filePicker = FilePicker.getInstance(this)

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
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKumDocumentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.topbar.title.text = "KUM Document Upload"

        intent.getStringExtra("ID_REQUEST")?.let { viewModel.creditRequestId = it }

        intent.getIntExtra("LIMIT", 0).let { limitAwal ->
            if(limitAwal < 50000000){
                binding.actionUploadNpwp.visibility = View.GONE
            }else{
                binding.actionUploadNpwp.visibility = View.VISIBLE
            }
        }

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

        binding.btnContinue.setOnClickListener {
            viewModel.loadTerms()
        }

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.onSubmitSuccess.observe(this){
            startActivity(
                Intent(this@KUMUploadDocumentsActivity, HomeActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
            this@KUMUploadDocumentsActivity.finish()
        }

        viewModel.onTermsLoaded.observe(this){
            val dialog = CreditTermsDialog().newInstance(it)
            dialog.listener = this@KUMUploadDocumentsActivity
            dialog.show(supportFragmentManager, null)
        }

        viewModel.onKTPSuccess.observe(this){
            binding.statusKtp.visibility = View.VISIBLE
            binding.actionUploadKtp.background = ContextCompat.getDrawable(this@KUMUploadDocumentsActivity, R.drawable.green_border_bg)
        }

        viewModel.onKKSuccess.observe(this){
            binding.statusKk.visibility = View.VISIBLE
            binding.actionUploadKk.background = ContextCompat.getDrawable(this@KUMUploadDocumentsActivity, R.drawable.green_border_bg)
        }

        viewModel.onNPWPSuccess.observe(this){
            binding.statusNpwp.visibility = View.VISIBLE
            binding.actionUploadNpwp.background = ContextCompat.getDrawable(this@KUMUploadDocumentsActivity, R.drawable.green_border_bg)
        }

        viewModel.onSelfieSuccess.observe(this){
            binding.statusSelfie.visibility = View.VISIBLE
            binding.actionUploadSelfie.background = ContextCompat.getDrawable(this@KUMUploadDocumentsActivity, R.drawable.green_border_bg)
        }

        viewModel.onSuratSuccess.observe(this){
            binding.statusSurat.visibility = View.VISIBLE
            binding.actionUploadSuratPengajuan.background = ContextCompat.getDrawable(this@KUMUploadDocumentsActivity, R.drawable.green_border_bg)
        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSubmit() {
        viewModel.submitFinal()
    }
}