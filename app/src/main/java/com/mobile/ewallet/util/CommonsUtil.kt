package com.mobile.ewallet.util

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.provider.OpenableColumns
import android.text.Editable
import android.text.TextWatcher
import android.webkit.MimeTypeMap
import android.widget.EditText
import androidx.exifinterface.media.ExifInterface
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.io.*
import java.net.URLEncoder
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


fun getMaxDateForBirthDate(): String {
    val cal = Calendar.getInstance()
    cal.add(Calendar.YEAR, -17)
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time)
}

fun createStringReqBody(value: String): RequestBody {
    return value.toRequestBody("text/plain".toMediaTypeOrNull())
}

fun createMultipartFromImageFile(file: File, key: String): MultipartBody.Part {
    return MultipartBody.Part.createFormData(
        key,
        file.name,
        file.asRequestBody("image/jpeg".toMediaTypeOrNull())
    )
}

fun createMultipartFromFile(file: File, key: String): MultipartBody.Part {
    return MultipartBody.Part.createFormData(
        key,
        file.name,
        file.asRequestBody(file.getExtension())
    )
}

fun File.getExtension(): MediaType? {
    var type: String? = null
    val encoded: String = try {
        URLEncoder.encode(name, "UTF-8").replace("+", "%20")
    } catch (e: Exception) {
        name
    }
    val extension = MimeTypeMap.getFileExtensionFromUrl(encoded)
    if (extension != null) {
        type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
    }

    return type?.toMediaType()
}

@Throws(IOException::class)
fun getFile(context: Context, uri: Uri): File {
    val destinationFilename = File(context.filesDir.path + File.separatorChar + queryName(context, uri))
    try {
        context.contentResolver.openInputStream(uri).use { ins ->
            ins?.let { createFileFromStream(it, destinationFilename) }
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return destinationFilename
}

fun createFileFromStream(ins: InputStream, destination: File?) {
    try {
        FileOutputStream(destination).use { os ->
            val buffer = ByteArray(4096)
            var length: Int
            while (ins.read(buffer).also { length = it } > 0) {
                os.write(buffer, 0, length)
            }
            os.flush()
        }
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
    }
}

private fun queryName(context: Context, uri: Uri): String {
    val returnCursor: Cursor = context.contentResolver.query(uri, null, null, null, null)!!
    val nameIndex: Int = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor.moveToFirst()
    val name: String = returnCursor.getString(nameIndex)
    returnCursor.close()
    return name
}

fun String.formatToCurrency(): String {
    val nf = NumberFormat.getCurrencyInstance()
    val decimalFormatSymbols: DecimalFormatSymbols = (nf as DecimalFormat).decimalFormatSymbols
    decimalFormatSymbols.currencySymbol = "Rp"
    nf.decimalFormatSymbols = decimalFormatSymbols
    val result =  nf.format(this.toDouble()).trim { it <= ' ' }
    return result.substring(0, result.length-3).replace(',', '.')

}

fun String.removeCharExceptNumber(): String {
    val re = Regex("[^0-9]")
    return re.replace(this, "")
}

fun String.unformatPhoneNUmber(): String {
    return replace('-', ' ').replace('+', ' ').replace("\\s".toRegex(), "")
}

fun getCameraPhotoOrientation(imagePath: String): Int {
    var rotate = 0
    try {
        //context.contentResolver.notifyChange(imageUri, null)
        val imageFile = File(imagePath)
        val exif = ExifInterface(imageFile.absolutePath)
        val orientation: Int = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_270 -> rotate = 270
            ExifInterface.ORIENTATION_ROTATE_180 -> rotate = 180
            ExifInterface.ORIENTATION_ROTATE_90 -> rotate = 90
        }
        Timber.i("Exif orientation: $orientation")
        Timber.i("Rotate value: $rotate")
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return rotate
}

fun rotateBitmap(source: Bitmap, angle: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
}

fun getBitmap(path: String): Bitmap? {
    var bitmap: Bitmap? = null
    try {
        val f = File(path)
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        bitmap = BitmapFactory.decodeStream(FileInputStream(f), null, options)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
    return bitmap
}

fun persistImage(bitmap: Bitmap, filesDirectory: File): String {
    val imageFile = File(filesDirectory, "img.jpg")
    val os: OutputStream
    try {
        os = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
        os.flush()
        os.close()
        return imageFile.path
    } catch (e: java.lang.Exception) {
        Timber.e("Error writing bitmap")
    }
    return ""
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}
