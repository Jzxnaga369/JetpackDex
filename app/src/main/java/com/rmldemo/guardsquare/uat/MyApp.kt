package com.rmldemo.guardsquare.uat

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.google.crypto.tink.config.TinkConfig
import com.rmldemo.guardsquare.uat.utils.Constans
import dagger.hilt.android.HiltAndroidApp
import java.util.UUID

@HiltAndroidApp
class MyApp : Application() {
    var updateTagId: ((String?) -> Unit)? = null

    override fun onCreate() {
        super.onCreate()
        // Membuat dan menyimpan UUID ketika pengguna pertama kali menjalankan aplikasi
        val sharedPreferences = this.getSharedPreferences(Constans.DATA_STORE, Context.MODE_PRIVATE)
        val isFirstOpen = sharedPreferences.getString(Constans.DEVICE_ID, "") ?: ""
        if (isFirstOpen == "") {
            val uuid = UUID.randomUUID().toString()
            sharedPreferences.edit().putString(Constans.DEVICE_ID, uuid).apply()
        }
        setContext(this)
        setSharedPreferences(sharedPreferences)
        TinkConfig.register()
    }

    companion object {
        private var context: Context? = null
        private var sharedPreferences: SharedPreferences? = null
        private var hardwareCallback: Boolean = true

        fun setContext(con: Context) {
            context=con
        }

        fun setSharedPreferences(newSharedPreferences: SharedPreferences) {
            sharedPreferences = newSharedPreferences
        }

        @JvmStatic
        fun myHardwareCallback(hardwareContext: Context?, hardwareInfo: Long) {
            if (hardwareContext == null) {
                Log.i("RASPCallback", "Context Null - Local hardware check triggered: $hardwareInfo")
                return
            } else {
                Toast.makeText(
                    context,
                    "Local hardware check triggered: $hardwareInfo",
                    Toast.LENGTH_SHORT
                ).show()
            }

//            if (hardwareCallback) {
//                Toast.makeText(
//                    context,
//                    "Local hardware check triggered: $hardwareInfo",
//                    Toast.LENGTH_SHORT
//                ).show()
//                hardwareCallback = false
//            }
        }

        @SuppressLint("HardwareIds")
        @JvmStatic
        fun getAuid(): String {
            // Mendapatkan UUID yang telah disimpan
            val savedUUID = if (sharedPreferences != null) {
                sharedPreferences?.getString(Constans.DEVICE_ID, "") ?: ""
            } else {
                "KOSONG"
            }
            val appUserId = if (context != null) {
                Settings.Secure.getString(context?.contentResolver, Settings.Secure.ANDROID_ID)
            } else {
                "KOSONG"
            }
            return "Android Id : ${appUserId}, UUID : $savedUUID"
        }
    }
}