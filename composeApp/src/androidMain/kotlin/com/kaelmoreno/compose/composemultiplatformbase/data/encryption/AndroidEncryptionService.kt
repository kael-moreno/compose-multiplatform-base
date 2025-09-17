package com.kaelmoreno.compose.composemultiplatformbase.data.encryption

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import com.kaelmoreno.compose.composemultiplatformbase.Logger
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class AndroidEncryptionService : EncryptionService {

    companion object {
        private const val TAG = "AndroidEncryptionService"
        private const val KEY_ALIAS = "DataStoreEncryptionKey"
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val CIPHER_TRANSFORMATION = "AES/CBC/PKCS7Padding"
        private const val IV_SEPARATOR = ":"
    }

    private val keyStore: KeyStore by lazy {
        KeyStore.getInstance(ANDROID_KEYSTORE).apply {
            load(null)
        }
    }

    init {
        generateKeyIfNeeded()
    }

    private fun generateKeyIfNeeded() {
        try {
            if (!keyStore.containsAlias(KEY_ALIAS)) {
                val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
                val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                    KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .setUserAuthenticationRequired(false)
                    .build()

                keyGenerator.init(keyGenParameterSpec)
                keyGenerator.generateKey()
                Logger.d("Encryption key generated successfully", TAG)
            }
        } catch (e: Exception) {
            Logger.e("Error generating encryption key", e, TAG)
        }
    }

    private fun getSecretKey(): SecretKey? {
        return try {
            keyStore.getKey(KEY_ALIAS, null) as? SecretKey
        } catch (e: Exception) {
            Logger.e("Error retrieving secret key", e, TAG)
            null
        }
    }

    override fun encrypt(data: String): String? {
        return try {
            val secretKey = getSecretKey() ?: return null
            val cipher = Cipher.getInstance(CIPHER_TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)

            val iv = cipher.iv
            val encryptedData = cipher.doFinal(data.toByteArray(Charsets.UTF_8))

            val ivBase64 = Base64.encodeToString(iv, Base64.DEFAULT)
            val encryptedBase64 = Base64.encodeToString(encryptedData, Base64.DEFAULT)

            "$ivBase64$IV_SEPARATOR$encryptedBase64"
        } catch (e: Exception) {
            Logger.e("Error encrypting data", e, TAG)
            null
        }
    }

    override fun decrypt(encryptedData: String): String? {
        return try {
            val secretKey = getSecretKey() ?: return null
            val parts = encryptedData.split(IV_SEPARATOR)
            if (parts.size != 2) return null

            val iv = Base64.decode(parts[0], Base64.DEFAULT)
            val encrypted = Base64.decode(parts[1], Base64.DEFAULT)

            val cipher = Cipher.getInstance(CIPHER_TRANSFORMATION)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))

            val decryptedData = cipher.doFinal(encrypted)
            String(decryptedData, Charsets.UTF_8)
        } catch (e: Exception) {
            Logger.e("Error decrypting data", e, TAG)
            null
        }
    }

    override fun isInitialized(): Boolean {
        return try {
            val secretKey = getSecretKey()
            if (secretKey != null) {
                // Test encryption/decryption
                val testData = "test_encryption"
                val encrypted = encrypt(testData)
                if (encrypted != null) {
                    val decrypted = decrypt(encrypted)
                    decrypted == testData
                } else {
                    false
                }
            } else {
                false
            }
        } catch (e: Exception) {
            Logger.e("Error checking initialization", e, TAG)
            false
        }
    }
}
