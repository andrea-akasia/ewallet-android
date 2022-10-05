package com.mobile.ewallet.feature.scantosendmoney

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityScannerBinding
import com.mobile.ewallet.feature.moneysend.SendMoneyViewModel
import com.mobile.ewallet.feature.pay.PayInputActivity
import com.mobile.ewallet.util.Constant
import com.mobile.ewallet.util.camera.QrCodeAnalyzer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ScannerActivity: BaseActivity<SendMoneyViewModel>(), QrCodeAnalyzer.BarcodeDetector {

    override val viewModelClass: Class<SendMoneyViewModel> get() = SendMoneyViewModel::class.java
    private lateinit var binding: ActivityScannerBinding

    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA
    )
    private lateinit var cameraExecutor: ExecutorService

    private fun allPermissionsGranted(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(
                    this, permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constant.RC_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Izin penggunakan kamera harus diberikan untuk dapat melanjutkan!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                startCamera()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cameraExecutor = Executors.newSingleThreadExecutor()

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this@ScannerActivity, REQUIRED_PERMISSIONS, Constant.RC_PERMISSIONS
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.previewView.surfaceProvider)
                }

            // Image analyzer
            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, QrCodeAnalyzer(this@ScannerActivity))
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalyzer
                )

            } catch (exc: Exception) {
                exc.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    override fun onBarcodeDetected(data: String) {
        //Toast.makeText(this, "BARCODE: $data", Toast.LENGTH_SHORT).show()
        //onBackPressedDispatcher.onBackPressed()
        startActivity(
            Intent(this, PayInputActivity::class.java)
                .putExtra("QR", data)
        )
        this@ScannerActivity.finish()
    }
}