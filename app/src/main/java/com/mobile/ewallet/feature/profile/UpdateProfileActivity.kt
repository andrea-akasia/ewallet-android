package com.mobile.ewallet.feature.profile

import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityUpdateProfileBinding
import com.mobile.ewallet.model.api.profile.ProfileAPIResponse
import com.mobile.ewallet.util.DatePickerFragment
import com.mobile.ewallet.util.getFile
import com.mobile.ewallet.util.getMaxDateForBirthDate
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File

class UpdateProfileActivity: BaseActivity<ProfileViewModel>(), DatePickerFragment.DateListener {

    override val viewModelClass: Class<ProfileViewModel> get() = ProfileViewModel::class.java
    private lateinit var binding: ActivityUpdateProfileBinding

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            viewModel.isNeedUpdatePhoto = true
            Timber.i("uri: $it")
            Timber.i("uri path: ${it.path}")
            //compressImageFile(File(it.path!!))
            viewModel.photoFile = getFile(this, it)
            Timber.i("file path: ${viewModel.photoFile!!.path}")
            Glide.with(this)
                .load(it)
                .into(binding.image)
        }
    }

    private fun compressImageFile(imageFile: File){
        lifecycleScope.launch {
            viewModel.photoFile = Compressor.compress(applicationContext, imageFile)
            //Timber.i("compressed image size: ${getFolderSizeLabel(viewModel.photoFile!!)}")
            Timber.i("compressed image path -> ${viewModel.photoFile!!.path}")

            val uri = Uri.fromFile(viewModel.photoFile)
            Glide.with(this@UpdateProfileActivity)
                .load(uri)
                .into(binding.image)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, android.R.color.white)
        window.insetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
        val prefixView =
            binding.tilPhone.findViewById<AppCompatTextView>(com.google.android.material.R.id.textinput_prefix_text)
        prefixView.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        prefixView.gravity = Gravity.CENTER

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.topbar.title.text = "Ubah Profile"

        intent.getStringExtra("PROFILE")?.let {
            viewModel.profileData = Gson().fromJson(it, ProfileAPIResponse::class.java)
            Glide.with(this)
                .load(viewModel.profileData!!.photoProfileThumbnail)
                .placeholder(R.drawable.user_placeholder)
                .into(binding.image)
            binding.etPhone.setText(viewModel.profileData!!.nOWA!!.substring(1, viewModel.profileData!!.nOWA!!.length))
            binding.etName.setText(viewModel.profileData!!.nama)
            binding.etDate.setText(viewModel.profileData!!.tanggalLahir)
        }

        binding.actionSelectPhoto.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.etDate.setOnClickListener {
            val datePicker = DatePickerFragment(maxDate = getMaxDateForBirthDate())
            datePicker.isCancelable = true
            datePicker.listener = this@UpdateProfileActivity
            datePicker.show(supportFragmentManager, null)
        }

        binding.btnSubmit.setOnClickListener {
            viewModel.save(
                phone = viewModel.profileData!!.nOWA!!,
                name = viewModel.profileData!!.nama!!,
                birthDate = binding.etDate.text.toString()
            )
        }

        observeViewModel()
    }

    override fun onDateSelected(date: String) {
        binding.etDate.setText(date)
    }

    private fun observeViewModel(){
        viewModel.onProfileSaved.observe(this){
            Toast.makeText(this, "profil berhasil disimpan", Toast.LENGTH_SHORT).show()
        }
        viewModel.isLoading.observe(this){
            if(it){
                binding.actionSelectPhoto.isClickable = false
                binding.btnSubmit.isClickable = false
                binding.btnSubmit.background = ContextCompat.getDrawable(this, R.drawable.orange_button_inactive_bg)
            }else{
                binding.actionSelectPhoto.isClickable = true
                binding.btnSubmit.isClickable = true
                binding.btnSubmit.background = ContextCompat.getDrawable(this, R.drawable.orange_button_bg)
            }
        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}