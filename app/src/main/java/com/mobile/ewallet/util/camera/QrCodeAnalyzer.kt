package com.mobile.ewallet.util.camera

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.gson.Gson
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import timber.log.Timber

class QrCodeAnalyzer(private val listener: BarcodeDetector) : ImageAnalysis.Analyzer {

    private var hasTrigerred = false

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy) {
        val img = image.image
        if (img != null) {
            val inputImage = InputImage.fromMediaImage(img, image.imageInfo.rotationDegrees)

            // Process image searching for barcodes
            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                .build()

            val scanner = BarcodeScanning.getClient(options)

            scanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    if(barcodes.isNotEmpty()){
                        if(!hasTrigerred){
                            hasTrigerred = true
                            Timber.i("addOnSuccessListener trigerred")
                            barcodes[0].rawValue?.let { listener.onBarcodeDetected(it) }
                        }
                    }
                    image.close()
                }
                .addOnFailureListener {
                    Timber.w("failure: ${it.localizedMessage}")
                    Timber.e(it)
                    image.close()
                }
        }
    }

    interface BarcodeDetector{
        fun onBarcodeDetected(data: String)
    }
}