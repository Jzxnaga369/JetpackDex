package com.rmldemo.guardsquare.uat.presentation

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.rmldemo.guardsquare.uat.MyApp
import com.rmldemo.guardsquare.uat.presentation.component.A11YBottomSheet
import com.rmldemo.guardsquare.uat.ui.theme.JetpackDexTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var nfcAdapter: NfcAdapter? = null

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        requestIntegrityToken()

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)


        setContent {
            JetpackDexTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var showBottomSheet by remember { mutableStateOf(false) }

                    LaunchedEffect(Unit) {
                        sharedFlow.collect {
                            showBottomSheet = true
                        }
                    }

                    if (showBottomSheet) {
                        A11YBottomSheet()
                    }

                    EWalletApp(
                        sharedPreferences = sharedPreferences
                    )

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_MUTABLE
        )
        val filters = arrayOf(IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED))

        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, filters, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.action == NfcAdapter.ACTION_TAG_DISCOVERED) {
            val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
            tag?.let {
                val tagId = it.id.toHexString()
                (applicationContext as MyApp).updateTagId?.invoke(tagId)
            }
        }
    }

    private fun ByteArray.toHexString(): String {
        val hexChars = "0123456789ABCDEF"
        val result = StringBuilder(size * 2)

        map { byte ->
            val value = byte.toInt()
            val hexChar1 = hexChars[value shr 4 and 0x0F]
            val hexChar2 = hexChars[value and 0x0F]
            result.append(hexChar1)
            result.append(hexChar2)
        }

        return result.toString()
    }

    companion object {
        private val _sharedFlow = MutableSharedFlow<Long>(replay = 1)
        val sharedFlow = _sharedFlow.asSharedFlow()

        @JvmStatic
        fun myA11YCallback(info: Long) {
            Log.i("MainActivity", "Potential Malware Detected: $info")
            CoroutineScope(Dispatchers.IO).launch {
                _sharedFlow.emit(info)
            }
        }
    }

//    fun generateNonce(): String {
//        return UUID.randomUUID().toString()
//    }
//
//    fun requestIntegrityToken() {
//        val integrityManager: IntegrityManager = IntegrityManagerFactory.create(applicationContext)
//        val nonce: String = generateNonce()
//
//        val integrityTokenRequest = IntegrityTokenRequest.builder()
//            .setNonce(nonce)
//            .setCloudProjectNumber(876374568681)
//            .build()
//
//        integrityManager.requestIntegrityToken(integrityTokenRequest)
//            .addOnSuccessListener { response ->
//                val token = response.token()
//                Log.d("Token Integrity", token)
//            }
//            .addOnFailureListener { e ->
//                Log.d("Token Integrity", e.message.toString())
//            }
//    }
}