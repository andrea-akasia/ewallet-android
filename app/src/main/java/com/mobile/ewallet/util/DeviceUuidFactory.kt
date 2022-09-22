package com.mobile.ewallet.util

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.telephony.TelephonyManager
import java.io.UnsupportedEncodingException
import java.util.*

@SuppressLint("HardwareIds")
class DeviceUuidFactory(context: Context) {

    protected var uuid: UUID? = null

    fun getDeviceUuid(): UUID? {
        return uuid
    }

    companion object {
        protected const val PREFS_FILE = "device_id.xml"
        protected const val PREFS_DEVICE_ID = "device_id"
    }

    init {
        if (uuid == null) {
            synchronized(DeviceUuidFactory::class.java) {
                if (uuid == null) {
                    val prefs =
                        context.getSharedPreferences(PREFS_FILE, 0)
                    val id =
                        prefs.getString(PREFS_DEVICE_ID, null)
                    if (id != null) {
                        // Use the ids previously computed and stored in the prefs file
                        uuid = UUID.fromString(id)
                    } else {
                        val androidId =
                            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

                        // Use the Android ID unless it's broken, in which case fallback on deviceId,
                        // unless it's not available, then fallback on a random number which we store
                        // to a prefs file
                        try {
                            if ("9774d56d682e549c" != androidId) {
                                uuid =
                                    UUID.nameUUIDFromBytes(androidId.toByteArray(charset("utf8")))
                            } else {
                                val deviceId =
                                    (context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).deviceId
                                uuid =
                                    if (deviceId != null) UUID.nameUUIDFromBytes(
                                        deviceId.toByteArray(charset("utf8"))
                                    ) else UUID.randomUUID()
                            }
                        } catch (e: UnsupportedEncodingException) {
                            throw RuntimeException(e)
                        }

                        // Write the value out to the prefs file
                        prefs.edit().putString(
                            PREFS_DEVICE_ID,
                            uuid.toString()
                        ).apply()
                    }
                }
            }
        }
    }
}