package com.mobile.ewallet.feature.credit.kum

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityKumDocumentBinding
import com.mobile.ewallet.util.getFile
import timber.log.Timber

class KUMUploadDocumentsActivity: BaseActivity<KUMDocumentViewModel>() {

    override val viewModelClass: Class<KUMDocumentViewModel> get() = KUMDocumentViewModel::class.java
    private lateinit var binding: ActivityKumDocumentBinding

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            if(viewModel.TAG == "KTP"){
                viewModel.uploadKTP(getFile(this, it))
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

        binding.actionUploadKtp.setOnClickListener {
            viewModel.TAG = "KTP"
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.onKTPSuccess.observe(this){
            binding.statusKtp.visibility = View.VISIBLE
            binding.actionUploadKtp.background = ContextCompat.getDrawable(this@KUMUploadDocumentsActivity, R.drawable.green_border_bg)
        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}