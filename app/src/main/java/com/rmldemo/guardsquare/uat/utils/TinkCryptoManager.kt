package com.rmldemo.guardsquare.uat.utils

import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.aead.AeadFactory
import java.util.Base64

object TinkCryptoManager {
    fun encrypt(plainText: String, keysetHandle: KeysetHandle): String {
        val aead: Aead = AeadFactory.getPrimitive(keysetHandle)
        val plaintextBytes = plainText.toByteArray(Charsets.UTF_8)
        val associatedData = "associatedData".toByteArray(Charsets.UTF_8)
        val ciphertext = aead.encrypt(plaintextBytes, associatedData)
        return android.util.Base64.encodeToString(ciphertext, android.util.Base64.NO_PADDING)
    }

    fun decrypt(cipherText: String, keysetHandle: KeysetHandle): String {
        val aead: Aead = AeadFactory.getPrimitive(keysetHandle)
        val ciphertextBytes = android.util.Base64.decode(cipherText, android.util.Base64.NO_PADDING)
        val associatedData = "associatedData".toByteArray(Charsets.UTF_8)
        val plaintext = aead.decrypt(ciphertextBytes, associatedData)
        return String(plaintext, Charsets.UTF_8)
    }
}