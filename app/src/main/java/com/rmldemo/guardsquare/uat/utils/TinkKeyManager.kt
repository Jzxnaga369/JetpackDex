package com.rmldemo.guardsquare.uat.utils

import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.aead.AeadKeyTemplates

object TinkKeyManager {
    init {
        AeadConfig.register()
    }

    fun generateKeysetHandle(): KeysetHandle {
        return KeysetHandle.generateNew(AeadKeyTemplates.AES256_GCM)
    }
}