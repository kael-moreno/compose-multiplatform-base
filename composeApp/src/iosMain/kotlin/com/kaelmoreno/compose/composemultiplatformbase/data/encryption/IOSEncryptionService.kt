package com.kaelmoreno.compose.composemultiplatformbase.data.encryption

import kotlinx.cinterop.*

@OptIn(ExperimentalForeignApi::class)
class IOSEncryptionService : EncryptionService {

    override fun encrypt(data: String): String? {
        return data
    }

    override fun decrypt(encryptedData: String): String? {
        return encryptedData
    }

    override fun isInitialized(): Boolean {
        return true
    }
}
